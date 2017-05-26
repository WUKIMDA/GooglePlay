package googleplay.kimda.com.googleplay.fragments;

import android.view.View;

import googleplay.kimda.com.googleplay.views.KimdaAsyncTask;


public class CategoryFragment extends BaseFragment {
    @Override
    public KimdaAsyncTask.Result doInbackground() {
        return KimdaAsyncTask.Result.ERROR;
    }

    @Override
    public View onPostExecute() {
        return null;
    }
}
