package googleplay.kimda.com.googleplay.fragments;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import googleplay.kimda.com.googleplay.adapters.HomeAdapter;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.views.KimdaAsyncTask;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class HomeFragment extends BaseFragment {

    private List<HomeBean.ListBean> mDataLists;

    @Override
    public KimdaAsyncTask.Result doInbackground() {

        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(Contans.URL_HOME_ROOT).build();

        try {
            //当前已经在子线程,不需要.enqueue(callBack)
            Response response = okHttpClient.newCall(request).execute();
            if (response.isSuccessful()) {
                ResponseBody responseBody = response.body();
                String gsonStr = responseBody.string();
                Gson gson = new Gson();
                HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
                if (homeBean == null) {
                    //虽然服务器没有报错，但是返回数据为空
                    Log.d("doInbackground", "EMPTY");
                    return KimdaAsyncTask.Result.EMPTY;
                } else {
                    mDataLists = homeBean.getList();
                    if (mDataLists != null && mDataLists.size() > 0) {
                        Log.d("doInbackground", "SUCCESS");
                        return KimdaAsyncTask.Result.SUCCESS;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.d("doInbackground", "error ");
            return KimdaAsyncTask.Result.ERROR;
        }

        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        //HomeFragment请求网络成功后填充的View
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new HomeAdapter(mDataLists));
        return listView;
    }


}
