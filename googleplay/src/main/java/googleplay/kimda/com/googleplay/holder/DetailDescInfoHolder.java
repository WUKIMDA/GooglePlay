package googleplay.kimda.com.googleplay.holder;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailDescInfoHolder extends BaseHolder<DetailBean> {
    View desView;
    public TextView mAppDetailDesTvDes;
    public TextView mAppDetailDesTvAuthor;
    public ImageView mAppDetailDesIvArrow;


    @Override
    public void refreshItem(DetailBean itemData) {
        mAppDetailDesTvDes.setText(itemData.getDes());
        mAppDetailDesTvAuthor.setText("出自: "+itemData.getAuthor());
    }
    /**通过布局树拿到的测量宽*/
    private int mWidth;

    @Override
    public void init() {
        this.mAppDetailDesTvDes = (TextView) desView.findViewById(R.id.app_detail_des_tv_des);
        this.mAppDetailDesTvAuthor = (TextView) desView.findViewById(R.id.app_detail_des_tv_author);
        this.mAppDetailDesIvArrow = (ImageView) desView.findViewById(R.id.app_detail_des_iv_arrow);

        //mAppDetailDesTvDes已经完全渲染，可以获取宽高值,为强行给mAppDetailDesTvDes设置
        mAppDetailDesTvDes.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mWidth = mAppDetailDesTvDes.getMeasuredWidth();
            }
        });

        //显示初始值显示100高度
        ViewGroup.LayoutParams desParams = mAppDetailDesTvDes.getLayoutParams();
        desParams.height = 100;
        //布局参数设置回去
        mAppDetailDesTvDes.setLayoutParams(desParams);

        desView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //执行动画
                startAnimator();
            }
        });
    }

    @Override
    public View getView() {
        desView = UiUtils.inflate(R.layout.item_app_detail_des);
        init();
        return desView;
    }

    private boolean isOpened;

    private void startAnimator() {
        //重新测量,通知父view测量，以便于能够保证获取到宽高
        //mAppDetailDesTvDes在XML定义的是Match--->EXACTLY指定模式
        mAppDetailDesTvDes.measure(View.MeasureSpec.makeMeasureSpec(mWidth, View.MeasureSpec.EXACTLY), 0);
        //获取测量的高:因为使用了属性动画会改变坐标,所以需要拿到真实的父控件测量出来的高
        int measuredHeight = mAppDetailDesTvDes.getMeasuredHeight();

        ValueAnimator valueAnimator;
        if (isOpened) {//true
            valueAnimator = ValueAnimator.ofFloat(measuredHeight, 100);
            isOpened = false;

            ObjectAnimator rotateAnimatorStart = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 0, 180);
            rotateAnimatorStart.setDuration(500);
            rotateAnimatorStart.start();
        } else {//false
            valueAnimator = ValueAnimator.ofFloat(100, measuredHeight);
            isOpened = true;

            ObjectAnimator rotateAnimatorStart = ObjectAnimator.ofFloat(mAppDetailDesIvArrow, "rotation", 180, 360);
            rotateAnimatorStart.setDuration(500);
            rotateAnimatorStart.start();
        }
        //监听动画更新:通过更新的值让LayoutParams进行动态高度的调整
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                Float animatedValue = (Float) animation.getAnimatedValue();
                ViewGroup.LayoutParams layoutParams = mAppDetailDesTvDes.getLayoutParams();
                layoutParams.height = animatedValue.intValue();
                mAppDetailDesTvDes.setLayoutParams(layoutParams);
            }
        });
        valueAnimator.setDuration(500);
        valueAnimator.start();
        //监听动画
        valueAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                //让scrollview滚动到最下方
                ViewParent parent = mAppDetailDesTvDes.getParent();
                while (parent instanceof ViewGroup){
                    parent = (ViewGroup)parent;
                    if (parent instanceof ScrollView){
                        ScrollView scrollView = (ScrollView) parent;
                        scrollView.fullScroll(View.FOCUS_DOWN);//方向:焦点向下,滚动到最后面
                    }
                    parent = parent.getParent();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {
            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });
    }
}
