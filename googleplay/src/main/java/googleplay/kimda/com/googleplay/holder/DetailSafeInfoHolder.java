package googleplay.kimda.com.googleplay.holder;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailSafeInfoHolder extends BaseHolder<DetailBean> {

    public ImageView mAppDetailSafeIvArrow;
    public LinearLayout mAppDetailSafePicContainer;
    public LinearLayout mAppDetailSafeDesContainer;
    View safeView;

    @Override
    public void refreshItem(DetailBean itemData) {
        List<DetailBean.SafeBean> safeBeanList = itemData.getSafe();
        //安全头
        for (int i = 0; i < safeBeanList.size(); i++) {
            DetailBean.SafeBean safeBean = safeBeanList.get(i);
            ImageView imageView = new ImageView(UiUtils.getContext());
            Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + safeBean.getSafeUrl()).into(imageView);
            mAppDetailSafePicContainer.addView(imageView);

            LinearLayout linearLayout = new LinearLayout(UiUtils.getContext());
            linearLayout.setOrientation(LinearLayout.HORIZONTAL);
            ImageView view = new ImageView(UiUtils.getContext());
            Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + safeBean.getSafeDesUrl()).into(view);
            TextView textView = new TextView(UiUtils.getContext());
            textView.setText(safeBean.getSafeDes());
            if (safeBean.getSafeDesColor() == 0) {
                textView.setTextColor(Color.parseColor("#c3c3c3"));
            } else {
                textView.setTextColor(Color.parseColor("#ff9000"));
            }

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(4, 2, 2, 4);
            params.gravity = Gravity.CENTER_VERTICAL;

            linearLayout.addView(view, params);
            linearLayout.addView(textView, params);
            mAppDetailSafeDesContainer.addView(linearLayout);
        }


    }

    @Override
    public void init() {
        mAppDetailSafeIvArrow = (ImageView) safeView.findViewById(R.id.app_detail_safe_iv_arrow);
        mAppDetailSafePicContainer = (LinearLayout) safeView.findViewById(R.id.app_detail_safe_pic_container);
        mAppDetailSafeDesContainer = (LinearLayout) safeView.findViewById(R.id.app_detail_safe_des_container);

        //第一次安全介绍,不能有动画
        ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
        layoutParams.height = 0;
        mAppDetailSafeDesContainer.setLayoutParams(layoutParams);


        safeView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行动画
                startAnimator();
            }
        });


    }

    private boolean isOpened;

    /**
     * 使用属性动画:因为会改变真实高度,下面会自动挤上来
     */
    private void startAnimator() {
        //重新测量不会改变位置值,改变位置值的是layout()方法
        mAppDetailSafeDesContainer.measure(0, 0);
        int measuredHeight = mAppDetailSafeDesContainer.getMeasuredHeight();

        ValueAnimator valueAnimator;
        if (isOpened) {//true
            valueAnimator = ValueAnimator.ofFloat(measuredHeight, 0);
            isOpened = false;
//            mAppDetailSafeIvArrow.setRotation();
            ObjectAnimator rotateAnimatorStart = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 0, 180);
            rotateAnimatorStart.setDuration(500);
            rotateAnimatorStart.start();
        } else {//false
            valueAnimator = ValueAnimator.ofFloat(0, measuredHeight);
            isOpened = true;
            ObjectAnimator rotateAnimatorEnd = ObjectAnimator.ofFloat(mAppDetailSafeIvArrow, "rotation", 180, 360);
            rotateAnimatorEnd.setDuration(500);
            rotateAnimatorEnd.start();
        }

        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animatedValue = (Float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAppDetailSafeDesContainer.getLayoutParams();
                layoutParams.height = animatedValue.intValue();
                mAppDetailSafeDesContainer.setLayoutParams(layoutParams);

            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();

    }

    @Override
    public View getView() {
        safeView = UiUtils.inflate(R.layout.item_app_detail_safe);
        init();
        return safeView;
    }
}
