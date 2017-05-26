package googleplay.kimda.com.googleplay.app;

import android.app.Application;

import com.umeng.analytics.MobclickAgent;

import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-25.
 */

public class GooglePlay extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //统计用户行为：友盟统计
        MobclickAgent.startWithConfigure(new MobclickAgent.UMAnalyticsConfig(getApplicationContext(), "5926990b4ad156098800125d", ""));//key,  密码


        //优化APP：启动内存检测工具（暂时不写）


        //第三方组件（推送，短信验证等）


        //启动自己的工具类
        UiUtils.init(this);
    }
}
