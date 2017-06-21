package googleplay.kimda.com.googleplay.manager;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.utils.CommonUtils;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 下载相关所有逻辑
 */

public class HmDownloadManager {

    public static final int STATE_UNDOWNLOAD = 0;
    public static final int STATE_WAITING = 1;
    public static final int STATE_DOWNLOADING = 2;
    public static final int STATE_PAUSE = 3;
    public static final int STATE_ERROR = 4;
    public static final int STATE_SUCCESS = 5;
    public static final int STATE_INSTALLED = 6;

    private static HmDownloadManager sInstance;

    public static synchronized HmDownloadManager getInstance() {
        if (sInstance == null) {
            sInstance = new HmDownloadManager();
        }
        return sInstance;
    }

    private HmDownloadManager() {
    }

    public void download(HomeBean.ListBean appItem, DownloadListener downloadListener) {
        int currentState = STATE_WAITING;
        downloadListener.onStateChange(appItem.getPackageName(), STATE_WAITING);
        Log.e("download", "当前状态是：" + currentState);
        //开启子线程，去下载
        DownloadTask downloadTask = new DownloadTask(appItem.getPackageName(), appItem.getDownloadUrl(), currentState, downloadListener);
        mTask.put(appItem.getPackageName(), downloadTask);
        ThreadManager.getDownloadPool().execute(downloadTask);
    }

    public void download(DetailBean detailBean, DownloadListener downloadListener) {
        int currentState = STATE_WAITING;
        downloadListener.onStateChange(detailBean.getPackageName(), STATE_WAITING);
        Log.e("download", "当前状态是：" + currentState);
        //开启子线程，去下载
        DownloadTask downloadTask = new DownloadTask(detailBean.getPackageName(), detailBean.getDownloadUrl(), currentState, downloadListener);
        mTask.put(detailBean.getPackageName(), downloadTask);
        ThreadManager.getDownloadPool().execute(downloadTask);
    }

    Map<String, DownloadTask> mTask = new HashMap<>();
    Map<String, DownloadListener> mListeners = new HashMap<>();

    public void addDownloadListener(String packname, DownloadListener downloadListener) {
        if (!mListeners.containsKey(packname)) {
            mListeners.put(packname, downloadListener);
        }
    }

    public int getState(String packageName, int fileMaxSize) {
        //蘑菇街的状态如何获取
        //1.是否安装最优先
        boolean installed = CommonUtils.isInstalled(UiUtils.getContext(), packageName);
        if (installed) {
            return STATE_INSTALLED;
        }
        //2.listener中可能有保存蘑菇街的状态
        DownloadListener downloadListener = mListeners.get(packageName);
        if (downloadListener != null) {
            return downloadListener.getCurrentState();
        } else {
            File apkFile = getDownloadFile(packageName);
            //3.到文件路径去找
            if (apkFile.exists()) {
                if (apkFile.length() >= fileMaxSize) {
                    //下载完成了
                    return STATE_SUCCESS;
                } else {
                    //半途而废
                    return STATE_PAUSE;
                }

            } else {
                return STATE_UNDOWNLOAD;
            }
        }
    }

    public void pause(String packageName) {
        //把美团网下载的task的isPause改成true
        DownloadTask downloadTask = mTask.get(packageName);
        if (downloadTask != null) {
            //确实有美团网的下载任务
            downloadTask.isPaused = true;
        }
    }

    public void cancel(String packageName) {
        DownloadTask downloadTask = mTask.get(packageName);
        if (downloadTask != null) {
            UiUtils.removeTask(downloadTask);
        }
    }

    public interface DownloadListener {
        void onStateChange(String packageName, int state);

        int getCurrentState();

        void onProgressChange(long progress);
    }

    private class DownloadTask implements Runnable {
        private DownloadListener downloadListener;
        private String packageName;
        private String downloadUrl;
        private int currentState;
        private long progress;
        private boolean isPaused = false;

        public DownloadTask(String packName, String downloadUrl, int currentState, DownloadListener downloadListener) {
            this.packageName = packName;
            this.downloadUrl = downloadUrl;
            this.currentState = currentState;
            this.downloadListener = downloadListener;
        }

        @Override
        public void run() {
            //续传第一个要改变的地方
            progress = getDownloadFile(packageName).length();
            Log.e("download", "开始下载");
            OkHttpClient okHttpClient = new OkHttpClient();
//            http://localhost:8080/GooglePlayServer/download?name=app/com.itheima.www/com.itheima.www.apk&range=0
            //续传第二个要改变的地方
            Request request = new Request.Builder().url(Contans.DOWNLOAD + "?name=" + downloadUrl + "&range=" + progress).build();
            try {
                //续传第三个要改变的地方
                FileOutputStream fos = new FileOutputStream(getDownloadFile(packageName), true);
                Response response = okHttpClient.newCall(request).execute();
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream(); //获取到下载的字节流
                    //TODO：不断读取字节流，再往文件中去写
                    byte[] buffer = new byte[1024];
                    int len = -1;
                    while ((len = inputStream.read(buffer)) != -1) {
                        fos.write(buffer, 0, len);
                        fos.flush();

                        currentState = STATE_DOWNLOADING;
                        Log.e("download", "当前状态是：" + currentState);
                        downloadListener.onStateChange(packageName, STATE_DOWNLOADING);

                        //TODO:刷新进度
                        progress += len;
                        downloadListener.onProgressChange(progress);

                        //TODO:中断循环，实现暂停效果
                        if (isPaused) {
                            break;
                        }
                    }
                    if (isPaused) {
                        currentState = STATE_PAUSE;
                        downloadListener.onStateChange(packageName, STATE_PAUSE);
                    } else {
                        //TODO:2.暂停要注意的地方！！！
                        currentState = STATE_SUCCESS;
                        Log.e("download", "当前状态是：" + currentState);
                        downloadListener.onStateChange(packageName, STATE_SUCCESS);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                currentState = STATE_ERROR;
                Log.e("download", "当前状态是：" + currentState);
                downloadListener.onStateChange(packageName, STATE_ERROR);

                //续传后虽然100%了，但是服务器报错
                getDownloadFile(packageName).delete();
            }

        }
    }

    public File getDownloadFile(String packName) {
        //sdcard(Android/data/googleplay36)    rom（cacheDir)
        File dir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //已挂载sdcard
            dir = new File(Environment.getExternalStorageDirectory(), "Android/data/com.heima.googleplay36/apk");
        } else {
            //rom  data/data/com.heima.googleplay36/cache
            dir = new File(UiUtils.getContext().getCacheDir(), "apk");
        }
        if (!dir.exists()) {
            dir.mkdirs(); //多级目录
        }
        File cacheFile = new File(dir, packName);
        return cacheFile;
    }
}
