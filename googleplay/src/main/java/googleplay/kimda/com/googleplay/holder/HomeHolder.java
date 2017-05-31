package googleplay.kimda.com.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.beans.HomeBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import it.sephiroth.android.library.picasso.Picasso;

/**
 * Created by BUTTON on 2017-05-26.
 */
//KIMDA:要做的只是初始化控件,可以View重新填充弄出控件再删除进行快捷初始化,然后refreshItem()进行数据的设置
public class HomeHolder extends BaseHolder<HomeBean.ListBean> {//注意  泛型在这里添加

    public ImageView mItemAppinfoIvIcon;
    public TextView mItemAppinfoTvTitle;
    public RatingBar mItemAppinfoRbStars;
    public TextView mItemAppinfoTvSize;
    public TextView mItemAppinfoTvDes;


    /**单一Item, 具体Bean数据,不是List集合*/
    @Override
    public void refreshItem(HomeBean.ListBean data) {
        //赋值
        mItemAppinfoTvTitle.setText(data.getName());
        mItemAppinfoTvSize.setText(Formatter.formatFileSize(UiUtils.getContext(),data.getSize()));
        mItemAppinfoTvDes.setText(data.getDes());
        mItemAppinfoRbStars.setRating(data.getStars());
        //Picasso加载图片
        Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + data.getIconUrl()).into(mItemAppinfoIvIcon);
    }

    public void init() {
        mItemAppinfoIvIcon = (ImageView) rootView.findViewById(R.id.item_appinfo_iv_icon);
        mItemAppinfoTvTitle = (TextView) rootView.findViewById(R.id.item_appinfo_tv_title);
        mItemAppinfoRbStars = (RatingBar) rootView.findViewById(R.id.item_appinfo_rb_stars);
        mItemAppinfoTvSize = (TextView) rootView.findViewById(R.id.item_appinfo_tv_size);
        mItemAppinfoTvDes = (TextView) rootView.findViewById(R.id.item_appinfo_tv_des);
    }

    public View rootView;

    @Override
    public View getView() {
        rootView = UiUtils.inflate(R.layout.item_home_app_info);
        init();
        return rootView;
    }


}
