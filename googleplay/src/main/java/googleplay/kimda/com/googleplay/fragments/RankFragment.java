package googleplay.kimda.com.googleplay.fragments;

import android.graphics.Color;
import android.graphics.drawable.StateListDrawable;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.protocol.RankProtocol;
import googleplay.kimda.com.googleplay.utils.UiUtils;
import googleplay.kimda.com.googleplay.views.DrawableUtils;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;
import googleplay.kimda.com.googleplay.views.MyFlowLayout;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class RankFragment extends BaseFragment {

    private ArrayList<String> data;

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        RankProtocol rankProtocol = new RankProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index","0");
        rankProtocol.setMapData(hashMap);
        try {
            data = rankProtocol.loadData();
            if (data != null && data.size() > 0) {
                return KimdaAsyncTask.Result.SUCCESS;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }

        return KimdaAsyncTask.Result.EMPTY;
    }

    @Override
    public View onPostExecute() {
        // 支持上下滑动
        ScrollView scrollView = new ScrollView(UiUtils.getContext());
        //FlowLayout flow = new FlowLayout(UIUtils.getContext());
        MyFlowLayout flow = new MyFlowLayout(UiUtils.getContext());

        int padding = UiUtils.dip2px(10);
        flow.setPadding(padding, padding, padding, padding);// 设置内边距

        //flow.setHorizontalSpacing(UIUtils.dip2px(6));// 水平间距
        //flow.setVerticalSpacing(UIUtils.dip2px(8));// 竖直间距

        for (int i = 0; i < data.size(); i++) {
            final String keyword = data.get(i);
            TextView view = new TextView(UiUtils.getContext());
            view.setText(keyword);

            view.setTextColor(Color.WHITE);
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);// 18sp
            view.setPadding(padding, padding, padding, padding);
            view.setGravity(Gravity.CENTER);

            // 生成随机颜色
            Random random = new Random();
            int r = 30 + random.nextInt(200);
            int g = 30 + random.nextInt(200);
            int b = 30 + random.nextInt(200);

            int color = 0xffcecece;// 按下后偏白的背景色

            StateListDrawable selector = DrawableUtils.getSelector(
                    Color.rgb(r, g, b), color, UiUtils.dip2px(6));
            view.setBackgroundDrawable(selector);

            flow.addView(view);

            // 只有设置点击事件, 状态选择器才起作用
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Toast.makeText(UiUtils.getContext(), keyword,
                            Toast.LENGTH_SHORT).show();
                }
            });
        }

        scrollView.addView(flow);
        return scrollView;
    }
}
