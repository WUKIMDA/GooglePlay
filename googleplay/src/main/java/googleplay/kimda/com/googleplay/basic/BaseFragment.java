package googleplay.kimda.com.googleplay.basic;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-25.
 */

public abstract class BaseFragment extends Fragment {


    private KimdaAsyncTask mKimdaAsyncTask;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //避免每次new多个KimdaAsyncTask
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
        return mKimdaAsyncTask.getCommonView();
    }

    //访问网络
    public abstract KimdaAsyncTask.Result doInbackground();

    //访问成功后返回的View
    public abstract View onPostExecute();


    /**
     * 当Activity创建的时候Fragment显示数据
     *
     * @param savedInstanceState
     */
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //不再关联Activity的时候进行网络请求(不再填充网络请求返回后返回的View,为懒加载做准备(选中这个页面后再访问网络请求))
        // mKimdaAsyncTask.getDataFromServer();

    }

    /**
     * 网络请求mKimdaAsyncTask.getDataFromServer();
     */
    public void loadData() {
        mKimdaAsyncTask.getDataFromServer();
    }

}
