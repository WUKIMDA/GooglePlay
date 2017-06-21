package googleplay.kimda.com.googleplay.activitys;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import java.util.HashMap;
import java.util.Map;

import googleplay.kimda.com.googleplay.basic.BaseActivity;
import googleplay.kimda.com.googleplay.basic.KimdaAsyncTask;
import googleplay.kimda.com.googleplay.beans.DetailBean;
import googleplay.kimda.com.googleplay.holder.DeatilDownLoadHolder;
import googleplay.kimda.com.googleplay.holder.DetailAppInfoHolder;
import googleplay.kimda.com.googleplay.holder.DetailAppPicsHolder;
import googleplay.kimda.com.googleplay.holder.DetailDescInfoHolder;
import googleplay.kimda.com.googleplay.holder.DetailHolder;
import googleplay.kimda.com.googleplay.holder.DetailSafeInfoHolder;
import googleplay.kimda.com.googleplay.holder.HomeHolder;
import googleplay.kimda.com.googleplay.protocol.DetailProtocal;

/**
 * Created by BUTTON on 2017-06-02.
 */

public class DetailActivity extends BaseActivity {
    private String mPackageName;
    private DetailBean mDetailBean;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //获取传递过来的包名
        Intent intent = getIntent();
        if (intent != null) {
            mPackageName = intent.getStringExtra(HomeHolder.PACKAGENAME);
        }
    }

    @Override
    public KimdaAsyncTask.Result doInbackground() {
        DetailProtocal detailProtocal = new DetailProtocal();
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("packageName", mPackageName);
        detailProtocal.setMapData(hashMap);
        try {
            mDetailBean = detailProtocal.loadData();
            if (mDetailBean == null) {
                return KimdaAsyncTask.Result.EMPTY;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return KimdaAsyncTask.Result.ERROR;
        }
        return KimdaAsyncTask.Result.SUCCESS;
    }


    @Override
    public View onPostExecute() {
        //Activity的Holder
        DetailHolder detailHolder = new DetailHolder();
        detailHolder.getView();
        detailHolder.setFinishActivity(new DetailHolder.FinishActivity() {
            @Override
            public void innerFinish() {
                finish();
            }
        });

        DetailAppInfoHolder infoHolder = new DetailAppInfoHolder();
        View infoView = infoHolder.getView();
        infoHolder.setData(mDetailBean);
        detailHolder.mDetailInfoContainer.addView(infoView);

        DetailSafeInfoHolder safeHolder = new DetailSafeInfoHolder();
        View safeView = safeHolder.getView();
        safeHolder.setData(mDetailBean);
        detailHolder.mDetailSafeContainer.addView(safeView);

        DetailAppPicsHolder picHolder = new DetailAppPicsHolder();
        View picView = picHolder.getView();
        picHolder.setData(mDetailBean);
        detailHolder.mDetailPicContainer.addView(picView);

        DetailDescInfoHolder descHolder = new DetailDescInfoHolder();
        View desView = descHolder.getView();
        descHolder.setData(mDetailBean);
        detailHolder.mDetailDesContainer.addView(desView);

        DeatilDownLoadHolder downLoadHolder = new DeatilDownLoadHolder();
        View downView = downLoadHolder.getView();
        downLoadHolder.setData(mDetailBean);
        detailHolder.mDetailDownloadContainer.addView(downView);

        //赋值后最终的View
        return detailHolder.getFinalView();

    }


}
