package googleplay.kimda.com.googleplay.adapters;

import java.util.List;

import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.AppHolder;
import googleplay.kimda.com.googleplay.holder.BaseHolder;

/**
 * Created by BUTTON on 2017-05-27.
 */

public class AppAdapter extends BasicAdapter<HomeBean.ListBean> {
    public AppAdapter(List<HomeBean.ListBean> data) {
        super(data);
    }

    @Override
    protected List<HomeBean.ListBean> getLoadMoreData() throws Exception {
        return null;
    }

    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        return new AppHolder();
    }
    //TODO:不支持加载更多
    @Override
    public boolean supportLayout() {
        return false;
    }


}
