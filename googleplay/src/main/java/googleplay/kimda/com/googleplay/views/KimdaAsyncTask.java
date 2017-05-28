package googleplay.kimda.com.googleplay.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.manager.ThreadManager;
import googleplay.kimda.com.googleplay.utils.UiUtils;

import static googleplay.kimda.com.googleplay.utils.UiUtils.getContext;

/**
 * FrameLayout显示页面的状态View,  本类处理网络+页面状态显示处理
 */
public abstract class KimdaAsyncTask {

    private FrameLayout mCommonView;

    public KimdaAsyncTask(Context context) {
        mCommonView = new FrameLayout(context);
        onPreExecute();
    }

    /**
     * 返回显示的状态加载的View页面
     *
     * @return
     */
    public FrameLayout getCommonView() {
        return mCommonView;
    }

    public static final int STATE_UNLOAD = 0;// 未加载
    public static final int STATE_LOADING = 1;// 正在加载(可能包含2)
    public static final int STATE_LOAD_ERROR = 2;// 加载失败

    //3和4是请求后,   可能包含2
    public static final int STATE_LOAD_EMPTY = 3;// 数据为空
    public static final int STATE_LOAD_SUCCESS = 4;// 访问成功

    public int mCurrentState = STATE_UNLOAD;// 当前状态
    private View mLoadView;
    private View mErrorView;
    private View mEmptyView;
    private View mSuccessView;

    private static final String TAG = "KimdaAsyncTask";

    /**
     * 网络请求前,第一次初始化
     */
    public void onPreExecute() {
        //全部初始化,并隐藏,根据状态显示

        //正在加载
        mLoadView = LayoutInflater.from(getContext()).inflate(R.layout.pager_loading, null);
        mLoadView.setVisibility(View.GONE);
        mCommonView.addView(mLoadView);

        //加载失败
        mErrorView = LayoutInflater.from(getContext()).inflate(R.layout.pager_error, null);
        mErrorView.setVisibility(View.GONE);
        mCommonView.addView(mErrorView);

        //空的,没有数据
        mEmptyView = LayoutInflater.from(getContext()).inflate(R.layout.pager_empty, null);
        mEmptyView.setVisibility(View.GONE);
        mCommonView.addView(mEmptyView);

        //请求成功后   1:有数据   2:没数据
    }

    private boolean isLoading;

    public void getDataFromServer() {
        if (mCurrentState == STATE_LOAD_SUCCESS) {
            return;
        }
        //避免手快而执行多个访问网络的子线程
        if (isLoading) {
            return;
        }
        //状态更改为正在加载
        isLoading = true;
        //第一次刷新,默认状态正在加载
        mCurrentState = STATE_LOADING;
        refreshUI();

        //访问网络
//        new Thread(mRunnableFirstServiceData()).start();

        //使用线程池
        ThreadManager.getNormalPool().execute(mRunnableFirstServiceData());



    }



    @NonNull
    private Runnable mRunnableFirstServiceData() {
        return new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "访问 Service");
                //根据枚举返回int结果
                Result result = getServiceData();
                mCurrentState = result.getResult();
                afreshRefreshUI();
            }
        };
    }

    /**
     * 重新刷新UI,主线程中
     */
    private void afreshRefreshUI() {
        UiUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refreshUI();
                //状态更改为加载完成
                isLoading = false;
            }
        });
    }

    //子Fragment访问网络并返回状态,因为子Fragment可能会使用不同的网络框架
    public abstract Result getServiceData();


    private void refreshUI() {
        if (mSuccessView == null && mCurrentState == STATE_LOAD_SUCCESS) {
            //请球成功后,子Fragment进行填充
            mSuccessView = getSuccessView();
            mCommonView.addView(mSuccessView);
        }
        mLoadView.setVisibility(mCurrentState == STATE_LOADING ? View.VISIBLE : View.GONE);
        mErrorView.setVisibility(mCurrentState == STATE_LOAD_ERROR ? View.VISIBLE : View.GONE);
        mEmptyView.setVisibility(mCurrentState == STATE_LOAD_EMPTY ? View.VISIBLE : View.GONE);
        if (mSuccessView != null) {
            mSuccessView.setVisibility(mCurrentState == STATE_LOAD_SUCCESS ? View.VISIBLE : View.GONE);
        }
    }

    //请求成功后,不知道Fragment的子页面是什么数据,抽象
    public abstract View getSuccessView();

    //STATE_LOAD_ERROR      STATE_LOAD_EMPTY        STATE_LOAD_SUCCESS
    public enum Result {
        SUCCESS(STATE_LOAD_SUCCESS), EMPTY(STATE_LOAD_EMPTY), ERROR(STATE_LOAD_ERROR);

        private int mState;

        Result(int state) {
            this.mState = state;
        }

        public int getResult() {
            return mState;
        }

    }


}

//
