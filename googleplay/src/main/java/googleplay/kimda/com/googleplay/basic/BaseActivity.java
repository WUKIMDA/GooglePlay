package googleplay.kimda.com.googleplay.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-06-02.
 */
public abstract class BaseActivity extends AppCompatActivity {
    private KimdaAsyncTask mKimdaAsyncTask;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mKimdaAsyncTask == null) {
            mKimdaAsyncTask = new KimdaAsyncTask(UiUtils.getContext()) {
                @Override
                public void onPreExecute() {  //第一步: 默认第一次全部初始化,但是不显示,根据网络状态显示
                    super.onPreExecute();
                }

                @Override
                public Result getServiceData() {// 第二步:网络请求,返回网络状态
                    return doInbackground();
                }

                @Override
                public View getSuccessView() { //第三步:根据网络状态返回要显示的View
                    return onPostExecute();
                }
            };
        }

        setContentView(mKimdaAsyncTask.getCommonView());

    }

    @Override
    protected void onStart() {
        super.onStart();
        loadData();//Activity开始可见的时候就开始第一次网络请求
    }

    public abstract KimdaAsyncTask.Result doInbackground();

    public abstract View onPostExecute();

    /**网络请求mKimdaAsyncTask.getDataFromServer();*/
    public void loadData() {
        mKimdaAsyncTask.getDataFromServer();
    }

}
