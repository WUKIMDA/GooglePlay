package googleplay.kimda.com.googleplay.holder;

import android.view.View;

/**
 * Created by BUTTON on 2017-05-26.
 */

/**
 * 子类自动填充rootView,只要写了构造
 * @param <T>
 */
public abstract class BaseHolder<T> {

    private T mItemData;

    /**单一Item, 具体Bean数据,不是List集合*/
    public void setData(T data) {
        this.mItemData = data;
        refreshItem(mItemData);
    }
    /**单一Item, 具体Bean数据,不是List集合*/
    public abstract void refreshItem(T itemData);

    /**初始化控件*/
    public abstract void init();

    public abstract View getView();

}
