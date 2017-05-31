package googleplay.kimda.com.googleplay.holder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.beans.SubjectBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-31.
 */

public class SubjectHolder extends BaseHolder<SubjectBean> {
    public View rootView;
    public ImageView mItemSubjectIvIcon;
    public TextView mItemSubjectTvTitle;


    @Override
    public void refreshItem(SubjectBean itemData) {
        mItemSubjectTvTitle.setText(itemData.getDes());
        Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + itemData.getUrl()).into(mItemSubjectIvIcon);
    }

    @Override
    public void init() {
        this.mItemSubjectIvIcon = (ImageView) rootView.findViewById(R.id.item_subject_iv_icon);
        this.mItemSubjectTvTitle = (TextView) rootView.findViewById(R.id.item_subject_tv_title);
    }

    @Override
    public View getView() {
        rootView = UiUtils.inflate(R.layout.item_subject);
        init();
        return rootView;
    }

}
