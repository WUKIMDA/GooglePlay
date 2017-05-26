package googleplay.kimda.com.googleplay.fragments;

import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.views.KimdaAsyncTask;



/**
 * Created by BUTTON on 2017-05-25.
 */

public class AppFragment extends BaseFragment {

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        return KimdaAsyncTask.Result.SUCCESS;
    }

    @Override
    public View onPostExecute() {
        TextView textView = new TextView(UiUtils.getContext());
        textView.setText("success");
        textView.setGravity(Gravity.CENTER);
        return textView;
    }
}
