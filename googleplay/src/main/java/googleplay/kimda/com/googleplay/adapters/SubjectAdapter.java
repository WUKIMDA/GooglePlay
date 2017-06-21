package googleplay.kimda.com.googleplay.adapters;

import java.util.List;

import googleplay.kimda.com.googleplay.basic.BasicAdapter;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.holder.SubjectHolder;

/**
 * Created by BUTTON on 2017-05-31.
 */

public class SubjectAdapter extends BasicAdapter {

    @Override
    public boolean supportLayout() {
        return false;
    }

    public SubjectAdapter(List data) {
        super(data);
    }

    @Override
    protected List getLoadMoreData() throws Exception {
        return null;
    }

    @Override
    public BaseHolder onCreateViewHolder(int position) {
        return new SubjectHolder();
    }
}
