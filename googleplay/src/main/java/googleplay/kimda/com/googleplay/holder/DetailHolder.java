package googleplay.kimda.com.googleplay.holder;

import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.basic.BaseHolder;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailHolder extends BaseHolder<DetailBean> {

    public Toolbar mDetailToolbar;
    public FrameLayout mDetailDownloadContainer;
    public FrameLayout mDetailInfoContainer;
    public FrameLayout mDetailSafeContainer;
    public FrameLayout mDetailPicContainer;
    public FrameLayout mDetailDesContainer;
    private View rootView;
    private FinishActivity mFinish;


    @Override
    public void refreshItem(DetailBean itemData) {

    }

    @Override
    public void init() {
        this.mDetailToolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        this.mDetailDownloadContainer = (FrameLayout) rootView.findViewById(R.id.detail_download_container);
        this.mDetailInfoContainer = (FrameLayout) rootView.findViewById(R.id.detail_info_container);
        this.mDetailSafeContainer = (FrameLayout) rootView.findViewById(R.id.detail_safe_container);
        this.mDetailPicContainer = (FrameLayout) rootView.findViewById(R.id.detail_pic_container);
        this.mDetailDesContainer = (FrameLayout) rootView.findViewById(R.id.detail_des_container);
        mDetailToolbar.setNavigationIcon(R.mipmap.ic_menu_back);
        mDetailToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mFinish.innerFinish();
            }
        });

    }

    public void setFinishActivity(FinishActivity finish){
        mFinish = finish;
    }

    public interface FinishActivity {
        public void innerFinish();
    }

    @Override
    public View getView() {
        rootView = UiUtils.inflate(R.layout.activity_app_detail);
        init();
        return rootView;
    }

    public View getFinalView() {
        if (rootView != null) {
            return rootView;
        }
        return null;
    }
}
