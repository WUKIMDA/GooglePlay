package googleplay.kimda.com.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by BUTTON on 2017-05-27.
 */

public class AppHolder extends BaseHolder<HomeBean.ListBean> {
    private  View rootView;
    public ImageView mItemAppinfoIvIcon;
    public TextView mItemAppinfoTvTitle;
    public RatingBar mItemAppinfoRbStars;
    public TextView mItemAppinfoTvSize;
    public TextView mItemAppinfoTvDes;

    @Override
    public void refreshItem(HomeBean.ListBean itemData) {
        //赋值
        mItemAppinfoTvTitle.setText(itemData.getName());
        mItemAppinfoTvSize.setText(Formatter.formatFileSize(UiUtils.getContext(),itemData.getSize()));
        mItemAppinfoTvDes.setText(itemData.getDes());
        mItemAppinfoRbStars.setRating(itemData.getStars());
        //Picasso加载图片
        Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE +itemData.getIconUrl()).into(mItemAppinfoIvIcon);
    }

    @Override
    public void init() {
        mItemAppinfoIvIcon = (ImageView) rootView.findViewById(R.id.item_appinfo_iv_icon);
        mItemAppinfoTvTitle = (TextView) rootView.findViewById(R.id.item_appinfo_tv_title);
        mItemAppinfoRbStars = (RatingBar) rootView.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppinfoTvSize = (TextView) rootView.findViewById(R.id.item_appinfo_tv_size);
        mItemAppinfoTvDes = (TextView) rootView.findViewById(R.id.item_appinfo_tv_des);
    }

    @Override
    public View getView() {
        rootView = UiUtils.inflate(R.layout.item_home_app_info);
        init();
        return rootView;
    }
}
