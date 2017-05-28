package googleplay.kimda.com.googleplay.adapters;

import com.google.gson.Gson;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.okhttp.ResponseBody;

import java.util.List;

import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.BaseHolder;
import googleplay.kimda.com.googleplay.holder.HomeHolder;
import googleplay.kimda.com.googleplay.utils.Contans;

/**
 * Created by BUTTON on 2017-05-26.
 */

public class HomeAdapter extends BasicAdapter {

    public HomeAdapter(List data) {
        super(data);
    }

//TODO:要做的只是传view给Holder
    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        return new HomeHolder();
    }



    @Override
    public List<HomeBean.ListBean> getLoadMoreData() throws Exception {
        OkHttpClient okHttpClient = new OkHttpClient();

        Request request = new Request.Builder().url(Contans.SERVER_URL+"home?index=" + mData.size()).build();

        Response response = okHttpClient.newCall(request).execute();
        if (response.isSuccessful()) {
            ResponseBody responseBody = response.body();
            String gsonStr = responseBody.string();
            Gson gson = new Gson();
            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
            if (homeBean != null) {
                List<HomeBean.ListBean> list = homeBean.getList();
                return list;
            }
        }
        return null;
    }
}
