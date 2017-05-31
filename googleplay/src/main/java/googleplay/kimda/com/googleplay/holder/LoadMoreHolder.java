package googleplay.kimda.com.googleplay.holder;

import android.view.View;
import android.widget.LinearLayout;

import googleplay.kimda.com.googleplay.R;
import googleplay.kimda.com.googleplay.adapters.BasicAdapter;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-28.
 */

public class LoadMoreHolder extends BaseHolder {
	private BasicAdapter mAdapter;
	View loadView;

	public LoadMoreHolder(BasicAdapter adapter) {
		mAdapter = adapter;
	}


	/**
	 * 不加载
	 */
	public static final int STATE_NONE = 0;
	/**
	 * 加载更多
	 */
	public static final int STATE_LOADING = 1;
	/**
	 * 加载失败,没有数据
	 */
	public static final int STATE_FAIL = 2;

	public LinearLayout mItemLoadmoreContainerLoading;
	public LinearLayout mItemLoadmoreContainerRetry;

	@Override
	public void init() {
		this.mItemLoadmoreContainerLoading = (LinearLayout) loadView.findViewById(R.id.item_loadmore_container_loading);
		this.mItemLoadmoreContainerRetry = (LinearLayout) loadView.findViewById(R.id.item_loadmore_container_retry);

		mItemLoadmoreContainerRetry.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//重新加载更多
				mAdapter.loadMoreData();
			}
		});


	}

	@Override
	public View getView() {
		loadView = UiUtils.inflate(R.layout.item_load_more);
		init();
		return loadView;
	}

	@Override
	public void refreshItem(Object itemData) {


	}

	public void setItemState(int state) {
		switch (state) {
			case STATE_NONE:
				mItemLoadmoreContainerLoading.setVisibility(View.GONE);
				mItemLoadmoreContainerRetry.setVisibility(View.GONE);
				break;
			case STATE_LOADING:
				mItemLoadmoreContainerLoading.setVisibility(View.VISIBLE);
				mItemLoadmoreContainerRetry.setVisibility(View.GONE);
				break;
			case STATE_FAIL:
				mItemLoadmoreContainerLoading.setVisibility(View.GONE);
				mItemLoadmoreContainerRetry.setVisibility(View.VISIBLE);
				break;
		}
	}


}
