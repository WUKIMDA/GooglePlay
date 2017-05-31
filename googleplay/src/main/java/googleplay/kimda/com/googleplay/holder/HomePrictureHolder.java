package googleplay.kimda.com.googleplay.holder;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.squareup.picasso.Picasso;

import java.util.List;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.animation.DepthPageTransformer;
import googleplay.kimda.com.googleplay.utils.Contans;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-30.
 */

public class HomePrictureHolder {
    public View rootView;
    public ViewPager mItemHomePicturePager;
    public LinearLayout mItemHomePictureContainerIndicator;
    private List<String> mPrictureList;
    private AutoPrictureTask mAutoPrictureTask;

    public View getView() {
        rootView = UiUtils.inflate(R.layout.item_home_picture);
        //填充后初始化
        init();
        return rootView;
    }

    public void init() {
        this.mItemHomePicturePager = (ViewPager) rootView.findViewById(R.id.item_home_picture_pager);
        this.mItemHomePictureContainerIndicator = (LinearLayout) rootView.findViewById(R.id.item_home_picture_container_indicator);
    }

    public void refreshItem(List<String> prictureList) {
        this.mPrictureList = prictureList;
        mItemHomePicturePager.setAdapter(mPagerAdapter);

        //初始化指示器
        for (int i = 0; i < mPrictureList.size(); i++) {
            View view = new View(UiUtils.getContext());
            if (i == 0) {
                view.setBackgroundResource(R.drawable.indicator_selected);
            } else {
                view.setBackgroundResource(R.drawable.indicator_normal);
            }
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(5, 5);
            layoutParams.leftMargin = 5;
            mItemHomePictureContainerIndicator.addView(view, layoutParams);
        }
        mItemHomePicturePager.setOnPageChangeListener(mPageChangeListener);

        mItemHomePicturePager.setPageTransformer(true,new DepthPageTransformer());

        mItemHomePicturePager.setCurrentItem((mPagerAdapter.getCount() / 2) - 1);

        //自动轮播
        mAutoPrictureTask = new AutoPrictureTask();
        mAutoPrictureTask.start();

        //触摸事件,触摸不滚动
        mItemHomePicturePager.setOnTouchListener(mOnTouchListener);
    }

    private View.OnTouchListener mOnTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_MOVE:
                    mAutoPrictureTask.stop();
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL://down后没有操作
                    mAutoPrictureTask.start();
                    break;
            }
            return false;
        }
    };


    public class AutoPrictureTask implements Runnable {

        public void stop() {
            UiUtils.removeTask(AutoPrictureTask.this);
        }

        public void start() {//激活内部任务
            UiUtils.postDelay(this, 2000);
        }

        @Override
        public void run() {
            UiUtils.logD(this.getClass(), "轮播中");
            //获取当前Item
            int currentItem = mItemHomePicturePager.getCurrentItem();
            //选中当前Item,延迟轮播
            mItemHomePicturePager.setCurrentItem(++currentItem);
            //延迟任务
            UiUtils.postDelay(this, 2500);
        }
    }

    ;

    public void stopAutoRunnable() {
        if (mAutoPrictureTask != null) {
            mAutoPrictureTask.stop();
        }
    }

    public void startAutoRunnable() {
        if (mAutoPrictureTask != null) {
            mAutoPrictureTask.start();
        }
    }


    private ViewPager.OnPageChangeListener mPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        }

        @Override
        public void onPageSelected(int position) {
            position = position % mPrictureList.size();
            //指示器切换
            for (int i = 0; i < mPrictureList.size(); i++) {
                View childAt = mItemHomePictureContainerIndicator.getChildAt(i);
                if (i == position) {
                    childAt.setBackgroundResource(R.drawable.indicator_selected);
                } else {
                    childAt.setBackgroundResource(R.drawable.indicator_normal);
                }
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {
        }
    };


    private PagerAdapter mPagerAdapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return mPrictureList.size() * 1000000;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public ImageView instantiateItem(ViewGroup container, int position) {
            position = position % mPrictureList.size();
            ImageView imageView = new ImageView(UiUtils.getContext());
            Picasso.with(UiUtils.getContext()).load(Contans.URL_IMAGE + mPrictureList.get(position)).into(imageView);
            container.addView(imageView);
            return imageView;
        }
    };


}
