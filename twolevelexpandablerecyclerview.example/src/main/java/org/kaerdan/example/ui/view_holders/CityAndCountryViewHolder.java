package org.kaerdan.example.ui.view_holders;

import android.view.View;
import android.widget.TextView;

import org.kaerdan.twolevelexpandablerecyclerview.ViewHolderWithSetter;

/**
 *
 */
public class CityAndCountryViewHolder extends ViewHolderWithSetter<String> {
    public CityAndCountryViewHolder(View itemView) {
        super(itemView);
    }

    @Override
    public void setItem(String item) {
        ((TextView) itemView).setText(item);
    }
}
