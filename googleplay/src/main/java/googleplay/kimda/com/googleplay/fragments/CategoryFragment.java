package googleplay.kimda.com.googleplay.fragments;

import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import googleplay.kimda.com.googleplay.basic.BaseFragment;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;
import googleplay.kimda.com.googleplay.beans.CategoryBean;
import googleplay.kimda.com.googleplay.beans.CategoryVo;
import googleplay.kimda.com.googleplay.utils.UiUtils;

import static googleplay.kimda.com.googleplay.beans.CategoryBean.TYPE_TITLE;

/**
 * Created by lidongzhi on 2017/6/1.
 */
public class CategoryFragment extends BaseFragment {


    private List<CategoryBean> mDatas;

    @Override
    public View onPostExecute() {
        ListView listView = new ListView(UiUtils.getContext());
//        listView.setAdapter(new CategoryAdapter(mDatas, R.layout.item_category, R.layout.item_category_title));
        return listView;
    }

    @Override
    public KimdaAsyncTask.Result doInbackground() {
//        CategoryProtocol categoryProtocol = new CategoryProtocol();
//        try {
//            List<CategoryVo> categoryVoList = categoryProtocol.loadData();
//            mDatas = transfer(categoryVoList);
//            if (categoryVoList == null || categoryVoList.size() == 0) {
//                return KimdaAsyncTask.Result.EMPTY;
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//            return KimdaAsyncTask.Result.ERROR;
//        }
        return KimdaAsyncTask.Result.ERROR;
    }

    private List<CategoryBean> transfer(List<CategoryVo> categoryVoList) {
        List<CategoryBean> categoryBeanList = new ArrayList<>();
        //改装javabean
        for (int i = 0; i < categoryVoList.size(); i++) {
            CategoryVo categoryVo = categoryVoList.get(i);
            //1个title类型的CategoryBean
            CategoryBean categoryTitleBean = new CategoryBean();
            categoryTitleBean.type = TYPE_TITLE;
            categoryTitleBean.title = categoryVo.title;
            categoryBeanList.add(categoryTitleBean);

            //4个normal类型的
            List<CategoryVo.InfosBean> infos = categoryVo.infos;
            for (int j = 0; j < infos.size(); j++) {
                CategoryVo.InfosBean infosBean = infos.get(j);
                CategoryBean categoryBean = new CategoryBean();
                categoryBean.type = CategoryBean.TYPE_NORMAL;
                categoryBean.url1 = infosBean.url1;
                categoryBean.url2 = infosBean.url2;
                categoryBean.url3 = infosBean.url3;

                categoryBean.name1 = infosBean.name1;
                categoryBean.name2 = infosBean.name2;
                categoryBean.name3 = infosBean.name3;
                categoryBeanList.add(categoryBean);
            }
        }
        return categoryBeanList;
    }

}
