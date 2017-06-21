package googleplay.kimda.com.googleplay.fragments;

import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.adapters.AppAdapter;
import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.protocol.AppProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;


/**
 * Created by BUTTON on 2017-05-25.
 */

public class AppFragment extends BaseFragment {


    private List<HomeBean.ListBean> mListBeanList;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        AppProtocol appProtocol = new AppProtocol();
        try {
            Map<String, String> hashMap = new HashMap<>();
            hashMap.put("index","0");
            appProtocol.setMapData(hashMap);
//            mListBeanList = appProtocol.loadData("0");
            mListBeanList = appProtocol.loadData();
            if (mListBeanList != null && mListBeanList.size() > 0) {
                return KimdaAsyncTask.Result.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }

        return KimdaAsyncTask.Result.EMPTY;

//        OkHttpClient okHttpClient = new OkHttpClient();
//        Request request = new Request.Builder().url(Contans.URL_HOME_ROOT).build();
//        try {
//            Response response = okHttpClient.newCall(request).execute();
//            ResponseBody responseBody = response.body();
//            String gsonStr = responseBody.string();
//            Gson gson = new Gson();
////            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
//            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
//
//
//            if (homeBean == null) {
//                return KimdaAsyncTask.Result.EMPTY;
//            } else {
//                mHomeBeanList = homeBean.getList();
//                if (mHomeBeanList != null && mHomeBeanList.size() > 0) {
//                    return KimdaAsyncTask.Result.SUCCESS;
//                }
//            }
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return KimdaAsyncTask.Result.ERROR;
//        }
//
//        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new AppAdapter(mListBeanList));
        return listView;
    }
}
