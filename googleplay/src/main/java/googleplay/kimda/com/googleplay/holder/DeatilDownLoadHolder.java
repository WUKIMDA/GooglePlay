package googleplay.kimda.com.googleplay.holder;

import android.util.Log;
import android.view.View;
import android.widget.Button;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.manager.HmDownloadManager;
import googleplay.kimda.com.googleplay.utils.CommonUtils;
import googleplay.kimda.com.googleplay.utils.UiUtils;

//
//import android.view.View;
//import android.widget.Button;
//import android.widget.Toast;
//
//import googleplay.kimda.com.googleplay.R;
//import googleplay.kimda.com.googleplay.basic.BaseHolder;
//import googleplay.kimda.com.googleplay.beans.DetailBean;
//import googleplay.kimda.com.googleplay.beans.DownLoadInfo;
//import googleplay.kimda.com.googleplay.manager.KimdaDownLoadManager;
//import googleplay.kimda.com.googleplay.utils.CommonUtils;
//import googleplay.kimda.com.googleplay.utils.UiUtils;
//
//import static googleplay.kimda.com.googleplay.manager.KimdaDownLoadManager.STATE_INSTALL;
//import static googleplay.kimda.com.googleplay.manager.KimdaDownLoadManager.STATE_SUCCESS;
//
//public class DeatilDownLoadHolder extends BaseHolder<DetailBean> implements KimdaDownLoadManager.DownloadObserver, View.OnClickListener {
//    public Button mAppDetailDownloadBtnFavo;
//    public Button mAppDetailDownloadBtnShare;
//    public Button mAppDetailDownloadBtnDownload;
//    private View downView;
//    private KimdaDownLoadManager mKDM;
//    private int mState;
//    private float mProgress;
//
//    @Override//初始化
//    public void refreshItem(DetailBean itemData) {
//        this.mItemData = itemData;
//        mKDM = KimdaDownLoadManager.getInstance();
//        //注册观察者
//        mKDM.registerObserver(this);
//
//        DownLoadInfo downLoadInfo = mKDM.getDownLoadInfo(itemData);
//        if (downLoadInfo == null) {//map没有缓存,没有下载过
//            mProgress = 0;
//            mState = KimdaDownLoadManager.STATE_NONE;
//        } else {//下载过
////            mState = downLoadInfo.mCurrentState;
//            mProgress = downLoadInfo.getProgress();
//            boolean installed = CommonUtils.isInstalled(UiUtils.getContext(), downLoadInfo.mPackageName);
//            if (installed) {//安装了
//                mState = KimdaDownLoadManager.STATE_INSTALL;//安装成功
//            } else {//没有安装
//                mState = KimdaDownLoadManager.STATE_SUCCESS;//下载成功
//            }
//
//
//
//        }
//
//        //根据状态刷新UI
//        refreshUI(mState, mProgress);
//    }
//
//    @Override
//    public void init() {
//
//        this.mAppDetailDownloadBtnFavo = (Button) downView.findViewById(R.id.app_detail_download_btn_favo);
//        this.mAppDetailDownloadBtnShare = (Button) downView.findViewById(R.id.app_detail_download_btn_share);
//        this.mAppDetailDownloadBtnDownload = (Button) downView.findViewById(R.id.app_detail_download_btn_download);
//
//        mAppDetailDownloadBtnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KimdaDownLoadManager downLoadManager = KimdaDownLoadManager.getInstance();
//                downLoadManager.download(mItemData);
//            }
//        });
//
//
//    }
//
//    @Override
//    public View getView() {
//        downView = UiUtils.inflate(R.layout.item_app_detail_bottom);
//        init();
//        return downView;
//    }
//
//
//    @Override
//    public void onDownloadStateChanged(DownLoadInfo info) {
//        refreshUIOnMainThread(info);
//    }
//
//
//    @Override
//    public void onDownloadProgressChanged(DownLoadInfo info) {
//        refreshUIOnMainThread(info);
//    }
//
//
//    /**
//     * 刷新UI的主线程
//     *
//     * @param info
//     */
//    private void refreshUIOnMainThread(final DownLoadInfo info) {
//        if (mItemData.getId() == info.mId) {
//            UiUtils.postTask(new Runnable() {
//                @Override
//                public void run() {
//                    refreshUI(info.mCurrentState, info.getProgress());//真正的刷新UI,传入状态和计算好的进度值
//                }
//            });
//        }
//    }
//
//    /**
//     * 真正的刷新UI,传入状态和计算好的进度值
//     *
//     * @param state
//     * @param progress
//     */
//    public void refreshUI(int state, float progress) {
//        mState = state;
//        mProgress = progress;
//        switch (state) {
//            case KimdaDownLoadManager.STATE_NONE:
//                mAppDetailDownloadBtnDownload.setText("下载");
//                break;
//            case KimdaDownLoadManager.STATE_DOWNLOAD:
//                mAppDetailDownloadBtnDownload.setText(String.valueOf(progress) + "%");
//                break;
//            case KimdaDownLoadManager.STATE_ERROR:
//                mAppDetailDownloadBtnDownload.setText("重试");
//                break;
//            case KimdaDownLoadManager.STATE_PAUSE:
//                mAppDetailDownloadBtnDownload.setText("继续");
//                break;
//            case STATE_SUCCESS:
//                mAppDetailDownloadBtnDownload.setText("安装");
//                break;
//            case KimdaDownLoadManager.STATE_WAITING:
//                mAppDetailDownloadBtnDownload.setText("等待");
//                break;
//        }
//
//    }
//
//
//    @Override
//    public void onClick(View v) {
//        if (v.getId() == R.id.app_detail_download_btn_download) {
//            System.out.println(mState);
//            switch (mState) {//根据当前状态决定下一步
//                case KimdaDownLoadManager.STATE_DOWNLOAD:
//                    mKDM.pause(mItemData);
//                    Toast.makeText(UiUtils.getContext(), "暂停下载", Toast.LENGTH_SHORT).show();
//                    break;
//                case KimdaDownLoadManager.STATE_ERROR:
//                    mKDM.download(mItemData);
//                    Toast.makeText(UiUtils.getContext(), "下载中", Toast.LENGTH_SHORT).show();
//                    break;
//                case KimdaDownLoadManager.STATE_NONE:
//                    Toast.makeText(UiUtils.getContext(), "下载中", Toast.LENGTH_SHORT).show();
//                    mKDM.download(mItemData);
//                    break;
//                case KimdaDownLoadManager.STATE_PAUSE:
//                    mKDM.download(mItemData);
//                    Toast.makeText(UiUtils.getContext(), "下载中", Toast.LENGTH_SHORT).show();
//                    break;
//                case STATE_INSTALL:
//                    mKDM.open(mItemData);
//                    Toast.makeText(UiUtils.getContext(), "正在打开", Toast.LENGTH_SHORT).show();
//                    break;
//                case STATE_SUCCESS:
//                    mKDM.install(mItemData);
//                    Toast.makeText(UiUtils.getContext(), "安装中", Toast.LENGTH_SHORT).show();
//                    break;
//                case KimdaDownLoadManager.STATE_WAITING:
//                    mKDM.cancle(mItemData);
//                    //1.取消后变成未下载状态
//                    mState = KimdaDownLoadManager.STATE_NONE;
//                    refreshUI(mState,0);
//                    Toast.makeText(UiUtils.getContext(), "取消成功", Toast.LENGTH_SHORT).show();
//                    break;
//            }
//
//        }
//    }
//}
public class DeatilDownLoadHolder extends BaseHolder<DetailBean> implements View.OnClickListener, HmDownloadManager.DownloadListener {
    public Button mAppDetailDownloadBtnFavo;
    public Button mAppDetailDownloadBtnShare;
    public Button mAppDetailDownloadBtnDownload;

    private View downView;
    private DetailBean detailBean;
    private int mState;
    private long mProgress;


    private void openApk() {
        if (CommonUtils.isInstalled(UiUtils.getContext(), detailBean.getPackageName())) {
            CommonUtils.openApp(UiUtils.getContext(), detailBean.getPackageName());
        }
    }

    private void install() {
        if (!CommonUtils.isInstalled(UiUtils.getContext(), detailBean.getPackageName())) {
            CommonUtils.installApp(UiUtils.getContext(), HmDownloadManager.getInstance().getDownloadFile(detailBean.getPackageName()));
        }
    }

    private void pause() {
        HmDownloadManager.getInstance().pause(detailBean.getPackageName());
    }

    private void cancel() {
        HmDownloadManager.getInstance().cancel(detailBean.getPackageName());
        //1.取消后变成未下载状态
        mState = HmDownloadManager.STATE_UNDOWNLOAD;
        refreshUibyState(mState);
    }

    private void download() {
        //让DownloadManger下载当前应用
        //TODO:想知道此刻下载的状态
        HmDownloadManager.getInstance().download(detailBean, this);
    }


    public void setData(DetailBean detailBean) {
        this.detailBean = detailBean;


    }

    @Override
    public void refreshItem(DetailBean itemData) {
        this.mItemData = itemData;
        //进入了页面，主动去获取状态
        mState = HmDownloadManager.getInstance().getState(detailBean.getPackageName(), detailBean.getSize());
        refreshUibyState(mState);
    }

    @Override
    public void init() {
        this.mAppDetailDownloadBtnFavo = (Button) downView.findViewById(R.id.app_detail_download_btn_favo);
        this.mAppDetailDownloadBtnShare = (Button) downView.findViewById(R.id.app_detail_download_btn_share);
        this.mAppDetailDownloadBtnDownload = (Button) downView.findViewById(R.id.app_detail_download_btn_download);

//        mAppDetailDownloadBtnDownload.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                KimdaDownLoadManager downLoadManager = KimdaDownLoadManager.getInstance();
//                downLoadManager.download(mItemData);
//            }
//        });
    }

    @Override
    public View getView() {
        downView = UiUtils.inflate(R.layout.item_app_detail_bottom);
        init();
        return downView;
    }

    public void onStateChange(String packageName, final int state) {
        Log.e("listener", "state:" + state);
        this.mState = state;
        UiUtils.postTask(new Runnable() {
            @Override
            public void run() {
                refreshUibyState(state);
            }
        });
    }

    public int getCurrentState() {
        return mState;
    }

    public void onProgressChange(long progress) {
        this.mProgress = progress;
    }

    private void refreshUibyState(int state) {
        switch (state) {
            case HmDownloadManager.STATE_UNDOWNLOAD:
                mAppDetailDownloadBtnDownload.setText("下载");
                break;
            case HmDownloadManager.STATE_WAITING:
                mAppDetailDownloadBtnDownload.setText("正在等待，点击取消");
                break;
            case HmDownloadManager.STATE_DOWNLOADING:
                int percent = (int) (mProgress * 100f / detailBean.getSize());
                mAppDetailDownloadBtnDownload.setText(percent + "%");
                break;
            case HmDownloadManager.STATE_PAUSE:
                mAppDetailDownloadBtnDownload.setText("继续下载");
                break;
            case HmDownloadManager.STATE_ERROR:
                mAppDetailDownloadBtnDownload.setText("重试");
                break;
            case HmDownloadManager.STATE_SUCCESS:
                mAppDetailDownloadBtnDownload.setText("安装");
                break;
            case HmDownloadManager.STATE_INSTALLED:
                mAppDetailDownloadBtnDownload.setText("打开");
                break;
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.app_detail_download_btn_download) {
            switch (mState) {
                case HmDownloadManager.STATE_UNDOWNLOAD:
                    download();
                    break;
                case HmDownloadManager.STATE_WAITING:
                    cancel();
                    break;
                case HmDownloadManager.STATE_DOWNLOADING:
                    pause();
                    break;
                case HmDownloadManager.STATE_PAUSE:
                    download(); //从35%继续下载
                    break;
                case HmDownloadManager.STATE_ERROR:
                    download();
                    break;
                case HmDownloadManager.STATE_SUCCESS:
                    install();
                    break;
                case HmDownloadManager.STATE_INSTALLED:
                    openApk();
                    break;
            }
        }
    }
}