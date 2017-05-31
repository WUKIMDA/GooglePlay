package googleplay.kimda.com.googleplay.protocol;

import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.beans.HomeBean;

/**
 * Created by BUTTON on 2017-05-30.
 */

public class GameProtocol extends BaseProtocal<List<HomeBean.ListBean>> {
    /**
     * 第一次,第二次以上要重写这个方法
     */
    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "game";
    }
}
