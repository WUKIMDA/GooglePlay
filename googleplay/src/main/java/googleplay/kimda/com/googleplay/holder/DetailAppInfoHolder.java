package googleplay.kimda.com.googleplay.holder;

import android.text.format.Formatter;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;

public class DetailAppInfoHolder extends BaseHolder<DetailBean> {
    public ImageView mAppDetailInfoIvIcon;
    public TextView mAppDetailInfoTvName;
    public RatingBar mAppDetailInfoRbStar;
    public TextView mAppDetailInfoTvDownloadnum;
    public TextView mAppDetailInfoTvVersion;
    public TextView mAppDetailInfoTvTime;
    public TextView mAppDetailInfoTvSize;
    private View infoView;


    @Override
    public void refreshItem(DetailBean itemData) {
        System.out.println("我没有被调用吗");
        System.out.println("-------------------"+itemData.toString());
        Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + itemData.getIconUrl())
                .placeholder(R.drawable.ic_default).error(R.drawable.ic_default).into(mAppDetailInfoIvIcon);
        mAppDetailInfoTvName.setText(itemData.getName());
        mAppDetailInfoRbStar.setRating((float) itemData.getStars());

        //使用了占位符
        mAppDetailInfoTvDownloadnum.setText(UiUtils.getContext().getString(R.string.detail_download_num, itemData.getDownloadNum()));
        mAppDetailInfoTvVersion.setText("版本号:" + itemData.getVersion());
        mAppDetailInfoTvTime.setText("时间:" + itemData.getDate());
        mAppDetailInfoTvSize.setText("大小:" + Formatter.formatFileSize(UiUtils.getContext(), itemData.getSize()));

    }

    @Override
    public void init() {
        mAppDetailInfoIvIcon = (ImageView) infoView.findViewById(R.id.app_detail_info_iv_icon);
        mAppDetailInfoTvName = (TextView) infoView.findViewById(R.id.app_detail_info_tv_name);
        mAppDetailInfoRbStar = (RatingBar) infoView.findViewById(R.id.app_detail_info_rb_star);
        mAppDetailInfoTvDownloadnum = (TextView) infoView.findViewById(R.id.app_detail_info_tv_downloadnum);
        mAppDetailInfoTvVersion = (TextView) infoView.findViewById(R.id.app_detail_info_tv_version);
        mAppDetailInfoTvTime = (TextView) infoView.findViewById(R.id.app_detail_info_tv_time);
        mAppDetailInfoTvSize = (TextView) infoView.findViewById(R.id.app_detail_info_tv_size);
    }

    public View getView() {
        infoView = UiUtils.inflate(R.layout.item_app_detail_info);
        init();
        return infoView;
    }
}