package googleplay.kimda.com.googleplay.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BasicAdapter;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.holder.HomeHolder;
import googleplay.kimda.com.googleplay.protocol.HomeProtocol;

/**
 * Created by BUTTON on 2017-05-26.
 */

public class HomeAdapter extends BasicAdapter {

    public HomeAdapter(List data) {
        super(data);
    }

    //KIMDA:要做的只是传view给Holder
    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        return new HomeHolder();
    }


    @Override
    public List<HomeBean.ListBean> getLoadMoreData() throws Exception {
        HomeProtocol homeProtocol = new HomeProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index",String.valueOf(mData.size()));
        homeProtocol.setMapData(hashMap);
//        HomeBean homeBean = homeProtocol.loadData(String.valueOf(mData.size()));
        HomeBean homeBean = homeProtocol.loadData();
        if (homeBean != null) {
            List<HomeBean.ListBean> list = homeBean.getList();
            return list;
        }
        return null;
//        OkHttpClient okHttpClient = new OkHttpClient();
//
//        Request request = new Request.Builder().url(Contans.SERVER_URL+"home?index=" + mData.size()).build();
//
//        Response response = okHttpClient.newCall(request).execute();
//        if (response.isSuccessful()) {
//            ResponseBody responseBody = response.body();
//            String gsonStr = responseBody.string();
//            Gson gson = new Gson();
//            HomeBean homeBean = gson.fromJson(gsonStr, HomeBean.class);
//            if (homeBean != null) {
//                List<HomeBean.ListBean> list = homeBean.getList();
//                return list;
//            }
//        }
//        return null;
    }
}
