package googleplay.kimda.com.googleplay.protocol;

import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseProtocal;
import googleplay.kimda.com.googleplay.beans.CategoryBean;

/**
 * Created by BUTTON on 2017-06-01.
 */

public class CategoryProtocol extends BaseProtocal<List<CategoryBean>> {
    @Override
    public void setMapData(Map<String, String> params) {
        this.mParams = params;
    }

    @Override
    public String getPager() {
        return "category";
    }
}
