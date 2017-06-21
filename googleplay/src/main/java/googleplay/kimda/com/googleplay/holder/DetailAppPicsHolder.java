package googleplay.kimda.com.googleplay.holder;


import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.views.SubjectImageLayout;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailAppPicsHolder extends BaseHolder<DetailBean> {
    View picView;
    public LinearLayout mAppDetailPicIvContainer;

    @Override
    public void refreshItem(DetailBean itemData) {
        List<String> screenList = itemData.getScreen();
        for (int i = 0; i < screenList.size(); i++) {
            SubjectImageLayout imageLayout = new SubjectImageLayout(UiUtils.getContext());
            imageLayout.setMode(SubjectImageLayout.RELATIVE_HEIGHT);
            imageLayout.setRatio(0.63f);

            ImageView imageView = new ImageView(UiUtils.getContext());
            Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + screenList.get(i)).into(imageView);

            //设置SubjectImageLayout和父布局的参数
            FrameLayout.LayoutParams subParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 150);//根据固定高度,动态调整宽度

            //设置SubjectImageLayout和父布局的参数
            FrameLayout.LayoutParams imageParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            imageLayout.addView(imageView,imageParams);
            mAppDetailPicIvContainer.addView(imageLayout,subParams);
        }
    }

    @Override
    public void init() {
        this.mAppDetailPicIvContainer = (LinearLayout) picView.findViewById(R.id.app_detail_pic_iv_container);

    }

    @Override
    public View getView() {
        picView = UiUtils.inflate(R.layout.item_app_detail_pic);
        init();
        return picView;
    }
}
