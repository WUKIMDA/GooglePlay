package googleplay.kimda.com.googleplay.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.FrameLayout;

import googleplay.kimda.com.googleplay.R;

/**
 * Created by BUTTON on 2017-05-31.
 */

public class SubjectImageLayout extends FrameLayout {
    private float mRatio = 2.42f;
    public static final int RELATIVE_WIDTH = 0;
    public static final int RELATIVE_HEIGHT = 1;
    private int mMode;

    public SubjectImageLayout(@NonNull Context context) {
        this(context, null);
    }

    public SubjectImageLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SubjectImageLayout);
        //参数2默认值
        mRatio = typedArray.getFloat(R.styleable.SubjectImageLayout_Ratio, 2.42f);
        mMode = typedArray.getInteger(R.styleable.SubjectImageLayout_Relative, RELATIVE_WIDTH);


        // typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //get width & hight 期望size
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int hightSize = MeasureSpec.getSize(heightMeasureSpec);

        //get width & hight mMode
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int hightMode = MeasureSpec.getMode(heightMeasureSpec);

        if (mMode == RELATIVE_WIDTH) {   //指定宽模式
            //2.测量自己的孩子
            //KIMDA :固定宽, 默认根据mRatio=2.42比率测量宽    //根据指定宽, 动态调整高度效果
            int childWidth = widthSize - getPaddingLeft() - getPaddingRight();
            int childHight = (int) (childWidth / mRatio);
            //测量孩子
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);//恰好,指定模式
            int childHightSpec = MeasureSpec.makeMeasureSpec(childHight, MeasureSpec.EXACTLY);//恰好,指定模式

            measureChildren(childWidthSpec, childHightSpec);

            //3.设置最终的自己宽高
            setMeasuredDimension(widthSize, childHight + getPaddingBottom() + getPaddingTop());


        } else if (mMode == RELATIVE_HEIGHT) {//指定高模式
            //2.测量自己的孩子
            int childHight = hightSize - getPaddingLeft() - getPaddingRight(); //50+
            int childWidth = (int) (childHight * mRatio);  //KIMDA:根据指定高, 动态调整宽度效果

            //测量孩子
            int childWidthSpec = MeasureSpec.makeMeasureSpec(childWidth, MeasureSpec.EXACTLY);//恰好,指定模式
            int childHightSpec = MeasureSpec.makeMeasureSpec(childHight, MeasureSpec.EXACTLY);//恰好,指定模式
            measureChildren(childWidthSpec, childHightSpec);

            //3.设置最终的自己宽高
            setMeasuredDimension(childWidth + getPaddingLeft() + getPaddingRight(), hightSize);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public float getRatio() {
        return mRatio;
    }

    /**设置比例
     * @param ratio
     */
    public void setRatio(float ratio) {
        mRatio = ratio;
    }

    public int getMode() {
        return mMode;
    }

    /**设置指定模式,width或者height
     * @param mode
     */
    public void setMode(int mode) {
        mMode = mode;
    }
}
