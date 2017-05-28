package googleplay.kimda.com.googleplay.adapters;

import android.os.SystemClock;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import googleplay.kimda.com.googleplay.holder.BaseHolder;
import googleplay.kimda.com.googleplay.holder.LoadMoreHolder;
import googleplay.kimda.com.googleplay.manager.ThreadManager;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-26.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    public List<T> mData;
    private T mT;

    public BasicAdapter(List<T> data) {
        this.mData = data;
    }

    public BasicAdapter(T data) {
        this.mT = data;
    }

    @Override
    public int getCount() {
        return mData.size() + 1;
    }

    @Override
    public T getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BaseHolder holder;
        if (convertView == null) {

            if (position == getCount() - 1) {
                //如果是正在加载,  那holder就是LoadMoreHolder
                holder = getLoadModeHolderInstance();
//                //加载更多:使用ViewHolder填充Item方式
                convertView = getLoadModeHolderInstance().getView();

                //3：设置标记
                convertView.setTag(holder);
            } else {
                //2：让子类Adapter自己重写ViewHolder来返回子类的Holder
                holder = onCreateViewHolder(position);
                //1.填充不同的布局(解决方案：传入不同的布局id)
//            convertView = holder.getView();
                convertView = holder.getView();

                //recond(记录)当前填充的View,传给Holder
                //3：设置标记
                convertView.setTag(holder);
            }


        } else {
            //4：获取标记
            holder = (BaseHolder) convertView.getTag();
        }
        //////////////////////////////////////////////////////////////
        if (position == getCount() - 1) {
            if (supportLayout()){//支持加载更多的Adapter
                //加载更多
                loadMoreData();
            }else{//不支持加载更多的Adapter
                mLoadMoreHolder.setItemState(LoadMoreHolder.STATE_NONE);
            }


        } else {
            //        onBindViewHolder(holder, position);
            //单一Item, 具体Bean数据,不是List集合
            holder.setData(getItem(position));
        }

        return convertView;
    }

    /**Adapter是否有加载更多的功能,默认true*/
    boolean isSupport = true;

    /**Adapter是否有加载更多的功能,默认true*/
    public boolean supportLayout(){
        return isSupport;
    }


    private boolean isLoadingMore;

    private void loadMoreData() {
        //加载更多的时候防止手快用户重复加载//isLoadingMore的开关
        if (isLoadingMore){
            return;
        }
        isLoadingMore = true;

        getLoadModeHolderInstance().setItemState(LoadMoreHolder.STATE_LOADING);

       // new Thread(mRunnableLoadMore).start();
        //使用线程池:特点:高效
        ThreadManager.getNormalPool().execute(mRunnableLoadMore);

    }

    private Runnable mRunnableLoadMore = new Runnable() {
        @Override
        public void run() {
            int state = LoadMoreHolder.STATE_LOADING;
            //加载更多数据
            List<T> loadMoreData = null;
            try {
                loadMoreData = getLoadMoreData();
                SystemClock.sleep(1000);
                if (loadMoreData.size() < 20){//不足20条数据
                    state=LoadMoreHolder.STATE_NONE;
                }

            } catch (Exception e) {
                e.printStackTrace();
                //加载失败
                state=LoadMoreHolder.STATE_FAIL;
            }
            if (loadMoreData != null) {
                mData.addAll(loadMoreData);
                //隐藏
                state=LoadMoreHolder.STATE_NONE;
            }

            //唤醒适配器,再刷新加载更多的状态
            final int finalState = state;
            UiUtils.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    notifyDataSetChanged();
                    mLoadMoreHolder.setItemState(finalState);
                    isLoadingMore = false;//恢复正在加载
                }
            });

        }
    };



    protected abstract List<T> getLoadMoreData() throws Exception;

    /**
     * 正常Item
     */
    public static final int TYPE_NORMAL = 0;
    /**
     * 正在加载的Item
     */
    public static final int TYPE_LOAD_MORE = 1;

    //当前position位置的时候是什么类型的Item
    @Override
    public int getItemViewType(int position) {
        if (position == getCount() - 1) {
            return TYPE_LOAD_MORE;
        }
        return TYPE_NORMAL;
    }

    //类型数
    @Override
    public int getViewTypeCount() {
        return super.getViewTypeCount() + 1;
    }

    public abstract BaseHolder<T> onCreateViewHolder(int position);

    LoadMoreHolder mLoadMoreHolder;


    /**
     * 保证LoadModeHolder只有一个(不重复)
     */
    public LoadMoreHolder getLoadModeHolderInstance() {
        if (mLoadMoreHolder == null) {
            mLoadMoreHolder = new LoadMoreHolder();
        }
        return mLoadMoreHolder;
    }


}