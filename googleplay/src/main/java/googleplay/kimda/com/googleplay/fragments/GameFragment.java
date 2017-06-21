package googleplay.kimda.com.googleplay.fragments;

import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;

import googleplay.kimda.com.googleplay.adapters.GameAdapter;
import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.protocol.GameProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class GameFragment extends BaseFragment {

    private List<HomeBean.ListBean> mListBeanList;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        GameProtocol gameProtocol = new GameProtocol();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("index","0");
        gameProtocol.setMapData(hashMap);

        try {
            mListBeanList = gameProtocol.loadData();
            if (mListBeanList != null && mListBeanList.size() > 0) {
                Log.d("load", "load success");
                return KimdaAsyncTask.Result.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
            Log.d("load", "load ERROR");
            return KimdaAsyncTask.Result.ERROR;
        }
        Log.d("load", "load EMPTY");
        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new GameAdapter(mListBeanList));
        return listView;
    }
}
