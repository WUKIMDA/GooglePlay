package googleplay.kimda.com.googleplay.adapters;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.basic.BasicAdapter;
import googleplay.kimda.com.googleplay.beans.CategoryBean;
import googleplay.kimda.com.googleplay.holder.CategoryHolder;
import googleplay.kimda.com.googleplay.protocol.CategoryProtocol;

/**
 * Created by BUTTON on 2017-06-01.
 */

public class CategoryAdapter extends BasicAdapter<CategoryBean> {


    private List<CategoryBean> mCategoryBeanList;

    public CategoryAdapter(CategoryBean data) {
        super(data);
    }

    @Override
    protected List<CategoryBean> getLoadMoreData() throws Exception {
        CategoryProtocol categoryProtocol = new CategoryProtocol();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("index","0");
        categoryProtocol.setMapData(hashMap);
        mCategoryBeanList = categoryProtocol.loadData();
        return mCategoryBeanList;
    }

    @Override
    public BaseHolder<CategoryBean> onCreateViewHolder(int position) {
        CategoryBean info = getItem(position);
//        if (info.isTitle()) {
//            //return new TitleHolder();// 标题栏holder
//        }
        return new CategoryHolder();// 普通类型holer
    }
}