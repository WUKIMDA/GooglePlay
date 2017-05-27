package googleplay.kimda.com.googleplay.adapters;

import java.util.List;

import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.holder.BaseHolder;
import googleplay.kimda.com.googleplay.holder.HomeHolder;

/**
 * Created by BUTTON on 2017-05-26.
 */

public class HomeAdapter extends BasicAdapter {


    public HomeAdapter(List data, int layoutId) {
        super(data, layoutId);
    }

//TODO:要做的只是传view给Holder
    @Override
    public BaseHolder<HomeBean.ListBean> onCreateViewHolder(int position) {
        return new HomeHolder(rootView);
    }
}
