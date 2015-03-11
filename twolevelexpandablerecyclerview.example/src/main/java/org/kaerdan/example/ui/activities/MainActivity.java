package org.kaerdan.example.ui.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;

import org.kaerdan.example.R;
import org.kaerdan.example.model.Country;
import org.kaerdan.example.ui.adapters.CityAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RecyclerView mainList = (RecyclerView) findViewById(R.id.main_list);
        mainList.setLayoutManager(new LinearLayoutManager(this));
        mainList.setAdapter(new CityAdapter(getCountryList()));
        mainList.setHasFixedSize(true);
    }

    private static List<Country> getCountryList() {
        List<Country> countries = new ArrayList<>();
        List<String> usaCities = Arrays.asList("New York", "Los Angeles", "Boston", "Dallas",
                "Seattle");
        countries.add(new Country("USA", usaCities));


        List<String> frenchCities = Arrays.asList("Paris", "Nantes", "Lyon", "Toulouse");
        countries.add(new Country("France", frenchCities));


        List<String> germanCities = Arrays.asList("Berlin", "Hamburg", "Frankfurt", "Munich",
                "Brunswick");
        countries.add(new Country("Germany ", germanCities ));

        return countries;
    }
}
