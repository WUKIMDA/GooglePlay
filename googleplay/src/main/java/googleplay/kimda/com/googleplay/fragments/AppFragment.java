package googleplay.kimda.com.googleplay.fragments;

import android.view.View;
import android.widget.ListView;

import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.adapters.AppAdapter;
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

public class AppFragment extends BaseFragment {


    private List<HomeBean.ListBean> mHomeBeanList;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder().url(Contans.URL_HOME_ROOT).build();
        try {
            Response response = okHttpClient.newCall(request).execute();
            ResponseBody responseBody = response.body();
            String gsonStr = responseBody.string();
            Gson gson = new Gson();
//            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);


            if (homeBean == null){
                return KimdaAsyncTask.Result.EMPTY;
            }else{
                mHomeBeanList = homeBean.getList();
                if (mHomeBeanList != null && mHomeBeanList.size() > 0) {
                    return KimdaAsyncTask.Result.SUCCESS;
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }

        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        ListView listView = new ListView(UiUtils.getContext());
                                                                //同样的Item布局
        listView.setAdapter(new AppAdapter(mHomeBeanList, R.layout.item_home_app_info));
        return listView;
    }
}
