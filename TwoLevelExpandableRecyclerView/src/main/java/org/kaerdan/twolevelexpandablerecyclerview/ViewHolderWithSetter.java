package org.kaerdan.twolevelexpandablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Represents View Holder for TwoLevelExpandableAdapter. Obtain data with method setItem().
 * @param <K> Type of data holder works with.
 */
public abstract class ViewHolderWithSetter<K> extends RecyclerView.ViewHolder {
    public ViewHolderWithSetter(View itemView) {
        super(itemView);
    }

    /**
     * Set your data here.
     * @param item data to be displayed.
     */
    public abstract void setItem(K item);

    /**
     * For top level view holders only. Override this method if you want to change
     * item click on which will cause an expanding and collapsing.
     * @param onClickListener
     */
    public void setExpandOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }
}