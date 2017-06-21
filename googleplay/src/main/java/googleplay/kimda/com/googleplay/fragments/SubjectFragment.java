package googleplay.kimda.com.googleplay.fragments;

import android.view.View;
import android.widget.ListView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.adapters.SubjectAdapter;
import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.beans.SubjectBean;
import googleplay.kimda.com.googleplay.protocol.SubjectProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class SubjectFragment extends BaseFragment {

    private List<SubjectBean> mSubjectBeanList;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        SubjectProtocol subjectProtocol = new SubjectProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index","0");
        subjectProtocol.setMapData(hashMap);
        try {
            mSubjectBeanList = subjectProtocol.loadData();
            if (mSubjectBeanList == null || mSubjectBeanList.size() == 0) {
                return KimdaAsyncTask.Result.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }

        return KimdaAsyncTask.Result.SUCCESS;
    }

    @Override
    public View onPostExecute() {
        ListView listView = new ListView(UiUtils.getContext());
        listView.setAdapter(new SubjectAdapter(mSubjectBeanList));
        return listView;
    }
}
