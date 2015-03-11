package org.kaerdan.twolevelexandablerecyclerview;

import android.support.v7.widget.RecyclerView;
import android.view.View;

public abstract class ViewHolderWithSetter<K> extends RecyclerView.ViewHolder {
    public ViewHolderWithSetter(View itemView) {
        super(itemView);
    }
    public abstract void setItem(K item);
    public void setExpandOnClickListener(View.OnClickListener onClickListener) {
        itemView.setOnClickListener(onClickListener);
    }
}