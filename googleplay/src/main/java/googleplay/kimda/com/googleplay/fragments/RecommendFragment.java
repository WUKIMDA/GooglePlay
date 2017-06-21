package googleplay.kimda.com.googleplay.fragments;

import android.view.View;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.adapters.RecommendAdapter;
import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.fly.StellarMap;
import googleplay.kimda.com.googleplay.protocol.RecommendProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class RecommendFragment extends BaseFragment {

    private List<String> mStringList;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        RecommendProtocol recommendProtocol = new RecommendProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index", "0");
        recommendProtocol.setMapData(hashMap);
        try {
            mStringList = recommendProtocol.loadData();
            if (mStringList != null && mStringList.size() > 0) {
                return KimdaAsyncTask.Result.SUCCESS;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }
        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        // 初始化飞入飞出自定义控件
        StellarMap stellar = new StellarMap(UiUtils.getContext());
        // 设置内部文字距边缘边距为10dip
        int padding = UiUtils.dip2px(10);
        stellar.setInnerPadding(padding, padding, padding, padding);
        // 设置数据源
        stellar.setAdapter(new RecommendAdapter(mStringList));
        // 设定展示规则,9行6列(具体以随机结果为准)
        stellar.setRegularity(6, 9);
        // 设置默认组为第0组
        stellar.setGroup(0, true);

        return stellar;
    }
}
