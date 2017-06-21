package googleplay.kimda.com.googleplay.protocol;

import java.util.ArrayList;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseProtocal;

public class RankProtocol extends BaseProtocal<ArrayList<String>> {

    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "hot";
    }
}