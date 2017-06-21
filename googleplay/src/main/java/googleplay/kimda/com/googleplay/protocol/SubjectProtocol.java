package googleplay.kimda.com.googleplay.protocol;

import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseProtocal;
import googleplay.kimda.com.googleplay.beans.SubjectBean;

/**
 * Created by BUTTON on 2017-05-31.
 */

public class SubjectProtocol  extends BaseProtocal<List<SubjectBean>> {
    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "subject";
    }
}
