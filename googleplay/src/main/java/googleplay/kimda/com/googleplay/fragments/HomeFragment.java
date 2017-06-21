package googleplay.kimda.com.googleplay.fragments;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.adapters.HomeAdapter;
import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.HomePrictureHolder;
import googleplay.kimda.com.googleplay.protocol.HomeProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;

/**
 * Created by BUTTON on 2017-05-25.
 */
public class HomeFragment extends BaseFragment {

    private List<HomeBean.ListBean> mDataLists;
    private ListView mListView;
    private List<String> mPictureList;
    private HomePrictureHolder mHomePrictureHolder;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        HomeProtocol homeProtocol = new HomeProtocol();
        try {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("index", "0");
            homeProtocol.setMapData(hashMap);
//            HomeBean homeBean = homeProtocol.loadData("0");
            HomeBean homeBean = homeProtocol.loadData();
            if (homeBean == null) {
                //虽然服务器没有报错，但是返回数据为空
                Log.d("doInbackground", "EMPTY");
                return KimdaAsyncTask.Result.EMPTY;
            } else {
                mDataLists = homeBean.getList();
                mPictureList = homeBean.getPicture();
                if (mDataLists != null && mDataLists.size() > 0) {
                    Log.d("doInbackground", "SUCCESS");
                    return KimdaAsyncTask.Result.SUCCESS;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("doInbackground", "error ");
            return KimdaAsyncTask.Result.ERROR;
        }
        return KimdaAsyncTask.Result.EMPTY;

//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder().url(Contans.URL_HOME_ROOT).build();
//
//        try {
//            //当前已经在子线程,不需要.enqueue(callBack)
//            Response response = okHttpClient.newCall(request).execute();
//            if (response.isSuccessful()) {
//                ResponseBody responseBody = response.body();
//                String gsonStr = responseBody.string();
//                Gson gson = new Gson();
//                HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
//                if (homeBean == null) {
//                    //虽然服务器没有报错，但是返回数据为空
//                    Log.d("doInbackground", "EMPTY");
//                    return KimdaAsyncTask.Result.EMPTY;
//                } else {
//                    mDataLists = homeBean.getList();
//                    if (mDataLists != null && mDataLists.size() > 0) {
//                        Log.d("doInbackground", "SUCCESS");
//                        return KimdaAsyncTask.Result.SUCCESS;
//                    }
//                }
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//            Log.d("doInbackground", "error ");
//            return KimdaAsyncTask.Result.ERROR;
//        }
//
//        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        //HomeFragment请求网络成功后填充的View
        mListView = new ListView(UiUtils.getContext());

        //轮播图
        mHomePrictureHolder = getHomePrictureHolder();
        //View视图
        View prictureView = mHomePrictureHolder.getView();
        //填充数据
        mHomePrictureHolder.refreshItem(mPictureList);

        mListView.addHeaderView(prictureView);

        mListView.setAdapter(new HomeAdapter(mDataLists));
        return mListView;
    }

    @Override
    public void onResume() {
        super.onResume();
        //获取对象
        mHomePrictureHolder = getHomePrictureHolder();
        //开始轮播
        mHomePrictureHolder.startAutoRunnable();
    }

    @Override
    public void onPause() {
        super.onPause();
        mHomePrictureHolder.stopAutoRunnable();
    }

    public HomePrictureHolder getHomePrictureHolder(){
        if (mHomePrictureHolder ==null){
            mHomePrictureHolder = new HomePrictureHolder();
        }
        return mHomePrictureHolder;
    }




}
