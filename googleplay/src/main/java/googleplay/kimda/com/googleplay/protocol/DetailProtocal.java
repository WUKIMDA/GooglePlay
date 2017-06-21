package googleplay.kimda.com.googleplay.protocol;


import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseProtocal;
import googleplay.kimda.com.googleplay.beans.DetailBean;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailProtocal extends BaseProtocal<DetailBean>{
    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "detail";
    }
}
