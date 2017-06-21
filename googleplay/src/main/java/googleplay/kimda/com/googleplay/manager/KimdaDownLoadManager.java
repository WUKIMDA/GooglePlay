//package googleplay.kimda.com.googleplay.manager;
//
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Map;
//
//import googleplay.kimda.com.googleplay.beans.DetailBean;
//import googleplay.kimda.com.googleplay.beans.DownLoadInfo;
//import googleplay.kimda.com.googleplay.utils.CommonUtils;
//import googleplay.kimda.com.googleplay.utils.Contans;
//import googleplay.kimda.com.googleplay.utils.UiUtils;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//
///**
// * 单例设计模式
// */
//public class KimdaDownLoadManager {
//    public static final int STATE_NONE = 0;// 下载未开始
//    public static final int STATE_WAITING = 1;// 等待下载
//    public static final int STATE_DOWNLOAD = 2;// 正在下载
//    public static final int STATE_PAUSE = 3;// 下载暂停
//    public static final int STATE_ERROR = 4;// 下载失败
//    public static final int STATE_SUCCESS = 5;// 下载成功
//    public static final int STATE_INSTALL = 6;// 安装
//
//    private KimdaDownLoadManager() {
//    }
//
//    private static volatile KimdaDownLoadManager mKimdaDownLoadManager;
//
//    //单例设计模式最终版
//    public static KimdaDownLoadManager getInstance() {
//        if (mKimdaDownLoadManager == null) {
//            synchronized (KimdaDownLoadManager.class) {
//                if (mKimdaDownLoadManager == null) {
//                    mKimdaDownLoadManager = new KimdaDownLoadManager();
//                }
//            }
//        }
//        return mKimdaDownLoadManager;
//    }
//
//    public void cancle(DetailBean itemData) {
//        DownLoadTask downLoadTask = mDownTaskMap.get(itemData.getId());
//        if (downLoadTask != null) {
//            UiUtils.removeTask(downLoadTask);
//
//        }
//
//    }
//
//    public void open(DetailBean itemData) {
//        if (CommonUtils.isInstalled(UiUtils.getContext(), itemData.getPackageName())) {
//            CommonUtils.openApp(UiUtils.getContext(), itemData.getPackageName());
//        }
//    }
//
//
//    /**
//     * 1. 定义下载观察者接口
//     */
//    public interface DownloadObserver {
//
//        // 下载状态发生变化
//        public void onDownloadStateChanged(DownLoadInfo info);
//
//        // 下载进度发生变化
//        public void onDownloadProgressChanged(DownLoadInfo info);
//    }
//
//    //数组,放的是观察者
//    private ArrayList<DownloadObserver> mObservers = new ArrayList<DownloadObserver>();
//
//    /**
//     * 注册观察者
//     *
//     * @param observer
//     */
//    public void registerObserver(DownloadObserver observer) {
//        if (mObservers != null && !mObservers.contains(observer)) {
//            mObservers.add(observer);
//        }
//    }
//
//    /**
//     * 注销观察者
//     *
//     * @param observer
//     */
//    public void removeObserver(DownloadObserver observer) {
//        if (mObservers != null && mObservers.contains(observer)) {
//            mObservers.remove(observer);
//        }
//    }
//
//    /**
//     * 监听下载状态改变
//     */
//    public void notifyDownLoadStateChange(DownLoadInfo info) {
//        for (DownloadObserver observer : mObservers) {
//            observer.onDownloadStateChanged(info);
//        }
//    }
//
//    /**
//     * 监听下载进度
//     */
//    public void notifyDownLoadProgressChange(DownLoadInfo info) {
//        for (DownloadObserver observer : mObservers) {
//            observer.onDownloadProgressChanged(info);
//        }
//    }
//
//
//    /**
//     * 多线程下载, 存放下载对象的app的信息,  id是唯一标识
//     */
//    Map<Integer, DownLoadInfo> mDownLoadMap = new HashMap<>();
//
//    /**
//     * 获取下载信息
//     */
//    public DownLoadInfo getDownLoadInfo(DetailBean detailBean) {
//        return mDownLoadMap.get(detailBean.getId());
//    }
//
//
//    /**
//     * 下载任务的集合, id是唯一标识
//     */
//    Map<Integer, DownLoadTask> mDownTaskMap = new HashMap<>();
//
//
//    public synchronized void download(DetailBean appInfo) {
//        if (appInfo != null) {
//            DownLoadInfo downLoadInfo = mDownLoadMap.get(appInfo.getId());
//            if (downLoadInfo == null) {
//                //拷贝下载信息并返回下载对象
//                downLoadInfo = downLoadInfo.copy(appInfo);
//
//            }
//
//            //单个下载任务: 还没开始执行,等待(有可能放入线程队列里等待)
//            downLoadInfo.mCurrentState = STATE_WAITING;
//            //各个下载对象的通知:各观察者根据此通知更新主界面
//            notifyDownLoadStateChange(downLoadInfo);
//            //下载对象记录
//            mDownLoadMap.put(appInfo.getId(), downLoadInfo);
//
//            //执行下载
//            DownLoadTask downLoadTask = new DownLoadTask(downLoadInfo);
//            ThreadManager.getDownloadPool().execute(downLoadTask);//下载线程池
//            //下载任务记录
//            mDownTaskMap.put(downLoadInfo.mId, downLoadTask);
//        }
//    }
//
//    /**
//     * 暂停下载:等待和下载状态都可以暂停
//     * KIMDA:**************************************************************************************************************************
//     *
//     * @param appInfo
//     */
//    public synchronized void pause(DetailBean appInfo) {
//        if (appInfo != null) {
//            DownLoadInfo downLoadInfo = mDownLoadMap.get(appInfo.getId());
//            if (downLoadInfo != null) {
//                int state = STATE_PAUSE;
//
//                if (state == STATE_PAUSE || state == STATE_WAITING) {
//                    //单个下载任务: 暂停(同停止)
//                    downLoadInfo.mCurrentState = STATE_PAUSE;
//                    //各个下载对象的通知:各观察者根据此通知更新主界面
//
//                    notifyDownLoadStateChange(downLoadInfo);
//
//                    DownLoadTask downLoadTask = mDownTaskMap.get(downLoadInfo.mId);
//                    if (downLoadTask != null) {
//                        //停止线程池的执行
//                        ThreadManager.getDownloadPool().execute(downLoadTask);
//                    }
//
//
//                }
//            }
//
//        }
//    }
//
//
//    /**
//     * 安装APK: KIMDA:*************************************************************************************************************************
//     *
//     * @param appInfo
//     */
//    public synchronized void install(DetailBean appInfo) {
//        /*
//         <activity android:name=".PackageInstallerActivity"
//                android:configChanges="orientation|keyboardHidden"
//                android:theme="@style/TallTitleBarTheme">
//            <intent-filter>
//                <action android:name="android.intent.action.VIEW" />
//                <category android:name="android.intent.category.DEFAULT" />
//                <data android:scheme="content" />
//                <data android:scheme="file" />
//                <data android:mimeType="application/vnd.android.package-archive" />
//            </intent-filter>
//        </activity>
//         */
////        DownLoadInfo downLoadInfo = mDownLoadMap.get(appInfo.getId());
////        Intent intent = new Intent();
////        //非Activity页面+Flag
////        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
////        intent.setAction("android.intent.action.VIEW");
////        intent.addCategory("android.intent.category.DEFAULT");
////        intent.setDataAndType(Uri.parse("file://" + downLoadInfo.mPath), "application/vnd.android.package-archive");
////        UiUtils.getContext().startActivity(intent);
//
//        DownLoadInfo downLoadInfo = mDownLoadMap.get(appInfo.getId());
//        //使用工具类
//        if (CommonUtils.isInstalled(UiUtils.getContext(), downLoadInfo.mPackageName)) {
//            CommonUtils.installApp(UiUtils.getContext(), downLoadInfo.mPath);
//        }
//    }
//
//    /**
//     * 下载过程 KIMDA***********************************************************************
//     */
//    private class DownLoadTask implements Runnable {
//        public DownLoadInfo download;
//
//        public DownLoadTask(DownLoadInfo info) {
//            this.download = info;
//        }
//
//        @Override
//        public void run() {
//            //使用okHttp框架下载-----字节流
//            OkHttpClient okHttpClient = new OkHttpClient();
//            Request request;
//            //状态:下载中
//            download.mCurrentState = STATE_DOWNLOAD;
//            notifyDownLoadStateChange(download);
//
//            //获取文件路径
//            File file = download.mPath;
//
//            //重头下载//第一次下载
//            if (!file.exists() || file.length() != download.mCurrentPos || download.mCurrentPos == 0) {//不存在,不同当前下载进度,当前进度是0
//                file.delete();
//                download.mCurrentPos = 0;//更新当前进度,重新下载
//                request = new Request.Builder().url(Contans.DOWNLOAD + "?name=" + download.mDownloadUrl + "&range=0").build();
//
//            } else {
//                download.mCurrentPos = file.length();//更新当前进度,续载
//                request = new Request.Builder().url(Contans.DOWNLOAD + "?name=" + download.mDownloadUrl + "&range=" + download.mCurrentPos).build();
//
//            }
//
//            //下载
//            FileOutputStream fos = null;
//            try {
//                Response response = okHttpClient.newCall(request).execute();//结果
//                if (response.isSuccessful()) {//成功
//                    InputStream inputStream = response.body().byteStream();
//                    byte[] buffer = new byte[1024 * 8];
//                    int len;
//                    fos = new FileOutputStream(file, true);//续传
//                    while ((len = inputStream.read(buffer)) != -1) {
//                        fos.write(buffer, 0, len);
//                        fos.flush();
//                        download.mCurrentPos += len;
//                        notifyDownLoadProgressChange(download);
//                    }
//                }
//                //KIMDA:判断是否完成下载
//            } catch (IOException e) {//下载失败
//                e.printStackTrace();
//                //状态: 请重试(错误)
//                download.mCurrentState = STATE_ERROR;
//                download.mCurrentPos = 0;
//                notifyDownLoadProgressChange(download);
//                //错误之后要把下载的apk删除
//                //file.delete();
//            }
//
//            if (file.length() >= download.mSize) {//完成下载
//                download.mCurrentState = STATE_SUCCESS;
//                notifyDownLoadProgressChange(download);
//
//            } else if (download.mCurrentState == STATE_PAUSE) { //暂停
//                notifyDownLoadProgressChange(download);
//            }
//
//            // 不管下载成功,失败还是暂停, 下载任务已经结束,都需要从当前任务集合中移除
//            //  mDownTaskMap.remove(download.mId);
//        }
//    }
//}
