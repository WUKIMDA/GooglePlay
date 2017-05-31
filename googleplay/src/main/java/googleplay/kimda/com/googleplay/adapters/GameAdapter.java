package googleplay.kimda.com.googleplay.adapters;

import java.util.HashMap;
import java.util.List;

import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.AppHolder;
import googleplay.kimda.com.googleplay.holder.BaseHolder;
import googleplay.kimda.com.googleplay.protocol.GameProtocol;

/**
 * Created by BUTTON on 2017-05-27.
 */

public class GameAdapter extends BasicAdapter<HomeBean.ListBean> {
    public GameAdapter(List<HomeBean.ListBean> data) {
        super(data);
    }

    @Override
    protected List<HomeBean.ListBean> getLoadMoreData() throws Exception {
        GameProtocol gameProtocol = new GameProtocol();

        HashMap<String, String> map = new HashMap<>();
        map.put("index", String.valueOf(mData.size()));
        gameProtocol.setMapData(map);

        List<HomeBean.ListBean> listBeanList = gameProtocol.loadData();
        return listBeanList;
    }

    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        //复用appHolder
        return new AppHolder();
    }

    //KIMDA:支持加载更多
    @Override
    public boolean supportLayout() {
        return true;
    }


}
