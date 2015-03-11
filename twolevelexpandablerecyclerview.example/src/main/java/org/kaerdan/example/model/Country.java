package org.kaerdan.example.model;

import org.kaerdan.twolevelexpandablerecyclerview.TwoLevelExpandableAdapter;

import java.util.List;

/**
 *
 */
public class Country implements TwoLevelExpandableAdapter.DataSet<String,String> {

    private final String name;
    private final List<String> cities;

    public Country(String name, List<String> cities) {
        this.name = name;
        this.cities = cities;
    }

    @Override
    public String getData() {
        return name;
    }

    @Override
    public List<String> getChildren() {
        return cities;
    }
}
