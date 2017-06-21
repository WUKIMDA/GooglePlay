package googleplay.kimda.com.googleplay.protocol;

import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseProtocal;
import googleplay.kimda.com.googleplay.beans.HomeBean;

/**
 * Created by BUTTON on 2017-05-30.
 */

public class HomeProtocol extends BaseProtocal<HomeBean> {
    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "home";
    }
//    public HomeBean loadData(String startIndex) throws Exception {
//        OkHttpClient okHttpClient = new OkHttpClient();
//        //url位置
//        Request request = new Request.Builder().url(Contans.URL_HOME_ROOT+startIndex).build();
//        //当前已经在子线程,不需要.enqueue(callBack)
//        Response response = okHttpClient.newCall(request).execute();
//        if (response.isSuccessful()) {
//            ResponseBody responseBody = response.body();
//            String gsonStr = responseBody.string();
//            Gson gson = new Gson();
//            return gson.fromJson(gsonStr, HomeBean.class);
//        } else {
//            return null;
//        }
//    }
}
