package googleplay.kimda.com.googleplay.beans;

import android.os.Environment;

import java.io.File;

import googleplay.kimda.com.googleplay.utils.UiUtils;

/**下载信息对象封装
 * Created by BUTTON on 2017-06-03.
 */

public class DownLoadInfo {
    public int mId;// apk唯一标识
    public long mSize;// apk大小
    public String mDownloadUrl;// 下载链接
    public String mPackageName;// apk名称
    public int mCurrentState;// 当前下载状态
    public long mCurrentPos;// 当前下载位置
    public File mPath;// apk下载在本地的路径


    /** apk保存路径
     * @param packageName
     * @return
     */
    private static File getDownLoadApkFile(String packageName) {
        File cacheDir;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            //说明已有SD卡存在
            cacheDir = new File(Environment.getExternalStorageDirectory(), "Android/data/com.kimda.googlePlay/apk");
        } else {
            cacheDir = new File(UiUtils.getContext().getCacheDir(), "apk");
        }
        //文件夹不存在
        if (!cacheDir.exists()) {
            cacheDir.mkdirs();//多级创建文件夹
        }
        File apkFile = new File(cacheDir,packageName+".apk");
        return apkFile;
    }

    /**
     * 根据应用信息,拷贝出一个下载对象
     */
    public static DownLoadInfo copy(DetailBean appInfo) {
        DownLoadInfo info = new DownLoadInfo();
        info.mId = appInfo.getId();
        info.mDownloadUrl = appInfo.getDownloadUrl();
        info.mSize = appInfo.getSize();
        info.mPackageName = appInfo.getName();
//        info.mCurrentState = KimdaDownLoadManager.STATE_NONE;
        info.mCurrentPos = 0;
        info.mPath = getDownLoadApkFile(info.mPackageName);
        return info;
    }

    public float getProgress(){
        if (mSize == 0){
            return 0;
        }
        return (float)(mCurrentPos*100/mSize);
    }

}
