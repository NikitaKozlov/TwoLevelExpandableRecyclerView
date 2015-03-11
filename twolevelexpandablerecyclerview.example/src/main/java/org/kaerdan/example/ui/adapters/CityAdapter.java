package org.kaerdan.example.ui.adapters;

import android.view.View;
import android.view.ViewGroup;

import org.kaerdan.example.R;
import org.kaerdan.example.model.Country;
import org.kaerdan.example.ui.view_holders.CityAndCountryViewHolder;
import org.kaerdan.twolevelexpandablerecyclerview.TwoLevelExpandableAdapter;
import org.kaerdan.twolevelexpandablerecyclerview.ViewHolderWithSetter;

import java.util.List;

/**
 *
 */
public class CityAdapter extends TwoLevelExpandableAdapter<Country> {

    public CityAdapter(List<Country> data) {
        super(data);
    }

    @Override
    public ViewHolderWithSetter getSecondLevelViewHolder(ViewGroup parent) {
        return new CityAndCountryViewHolder(View.inflate(parent.getContext(),
                R.layout.list_item_city, null));
    }

    @Override
    public ViewHolderWithSetter getTopLevelViewHolder(ViewGroup parent) {
        return new CityAndCountryViewHolder(View.inflate(parent.getContext(),
                R.layout.list_item_country, null));
    }
}
