package googleplay.kimda.com.googleplay.adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import googleplay.kimda.com.googleplay.holder.BaseHolder;
import googleplay.kimda.com.googleplay.utils.UiUtils;

/**
 * Created by BUTTON on 2017-05-26.
 */
public abstract class BasicAdapter<T> extends BaseAdapter {
    View rootView;
    private int mLayoutId;
    private List<T> mData;
    private T mT;

    public BasicAdapter(List<T> data,int layoutId) {
        this.mData = data;
        mLayoutId = layoutId;
    }
    public BasicAdapter(T data,int layoutId) {
        this.mT = data;
        mLayoutId = layoutId;
    }

    @Override
    public int getCount() {
        return mData.size();
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
        BaseHolder<T> holder;
        if (convertView == null) {

            //1.填充不同的布局(解决方案：传入不同的布局id)
//            convertView = holder.getView();
            convertView = View.inflate(UiUtils.getContext(),mLayoutId,null);
            //recond(记录)当前填充的View,传给Holder
            rootView =convertView;
            //2：让子类Adapter自己重写ViewHolder来返回子类的Holder
            holder = onCreateViewHolder(position);


            //3：设置标记
            convertView.setTag(holder);
        } else {
            //4：获取标记
            holder = (BaseHolder<T>) convertView.getTag();
        }

//        onBindViewHolder(holder, position);
        //单一Item, 具体Bean数据,不是List集合
        holder.setData(getItem(position));


        return convertView;

    }


    public abstract BaseHolder<T> onCreateViewHolder(int position);

}