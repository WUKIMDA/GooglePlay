package googleplay.kimda.com.googleplay.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BasicAdapter;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.AppHolder;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.protocol.AppProtocol;

/**
 * Created by BUTTON on 2017-05-27.
 */

public class AppAdapter extends BasicAdapter<HomeBean.ListBean> {
    public AppAdapter(List<HomeBean.ListBean> data) {
        super(data);
    }

    @Override
    protected List<HomeBean.ListBean> getLoadMoreData() throws Exception {
        AppProtocol appProtocol = new AppProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index",String.valueOf(mData.size()));
        appProtocol.setMapData(hashMap);
//        List<HomeBean.ListBean> listBeanList = appProtocol.loadData(String.valueOf(mData.size()));
        List<HomeBean.ListBean> listBeanList = appProtocol.loadData();
        return listBeanList;
    }

    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        return new AppHolder();
    }
    //KIMDA:支持加载更多
    @Override
    public boolean supportLayout() {
        return true;
    }


}
