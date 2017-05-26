package googleplay.kimda.com.googleplay.fragments;

import android.view.View;

import googleplay.kimda.com.googleplay.views.KimdaAsyncTask;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class RecommendFragment extends BaseFragment {
    @Override
    public KimdaAsyncTask.Result doInbackground() {
        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        return null;
    }
}
