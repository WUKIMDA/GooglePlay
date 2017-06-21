package googleplay.kimda.com.googleplay.adapters;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BasicAdapter;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.holder.RankHolder;
import googleplay.kimda.com.googleplay.protocol.RankProtocol;

/**
 * Created by BUTTON on 2017-06-01.
 */

public class RankAdapter extends BasicAdapter {

    private ArrayList<String> mArrayList;

    public RankAdapter(List data) {
        super(data);
    }

    @Override
    protected List getLoadMoreData() throws Exception {
        RankProtocol hotProtocol = new RankProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index","0");
        hotProtocol.setMapData(hashMap);
        mArrayList = hotProtocol.loadData();

        return mArrayList;
    }

    @Override
    public BaseHolder onCreateViewHolder(int position) {
        return new RankHolder();
    }
}
