package com.appdev.matthewa.fema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Locale;

public class CenterHomeActivity extends AppCompatActivity {
    private ListView loadLocations;
    private LocationAdapter adapter;
    private DisasterLocation selectedLocation;
    private DisasterLocation[] locations;
    private TextView neededFood, neededClothes, neededWater;
    private EditText sentFood, sentClothes, sentWater;
    private Button submitDonations, logout;
    private FEMADatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_home_page);
        setTitle("Center Home Page");

        db = FEMADatabase.getDatabase(this);

        neededFood = findViewById(R.id.food_needed);
        neededClothes = findViewById(R.id.clothes_needed);
        neededWater = findViewById(R.id.water_needed);
        loadLocations = findViewById(R.id.show_all_disaster_locations);
        new RetrieveAllLocations().execute();

        loadLocations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadLocations.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        loadLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (selectedLocation == adapter.getItem(position)) {
                    selectedLocation = null;
                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));

                    neededFood.setText(null);
                    neededClothes.setText(null);
                    neededWater.setText(null);
                }

                else {
                    loadLocations.setBackgroundColor(getResources().getColor(android.R.color.background_light));

                    selectedLocation = adapter.getItem(position);
                    view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

                    neededFood.setText(String.format(Locale.getDefault(), "%d", selectedLocation.getNumFoodCans()));
                    neededClothes.setText(String.format(Locale.getDefault(), "%d", selectedLocation.getNumClothing()));
                    neededWater.setText(String.format(Locale.getDefault(), "%d", selectedLocation.getNumWaterBottles()));
                }
            }
        });

        sentFood = findViewById(R.id.food_contributed);
        sentClothes = findViewById(R.id.clothes_contributed);
        sentWater = findViewById(R.id.water_contributed);

        submitDonations = findViewById(R.id.submit_donations);
        submitDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new UpdateLocationNeeds().execute();
            }
        });

        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private class RetrieveAllLocations extends AsyncTask<Void, Void, DisasterLocation[]> {
        @Override
        protected DisasterLocation[] doInBackground(Void... voids) {
            locations = db.disasterLocationsDAO().getAllLocations();
            return locations;
        }

        @Override
        protected void onPostExecute(DisasterLocation[] topVotes) {
            if(locations.length > 0) {
                adapter = new LocationAdapter(locations);
                loadLocations.setAdapter(adapter);
            }
            else {
                Toast.makeText(CenterHomeActivity.this, "There are currently no cities in need", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class UpdateLocationNeeds extends AsyncTask<Void, Void, DisasterLocation> {
        @Override
        protected DisasterLocation doInBackground(Void... voids) {
            DisasterLocation locationNeeds = db.disasterLocationsDAO().findLocation(selectedLocation.getCity(), selectedLocation.getPostalCode());

            int newFoodNeeds = locationNeeds.getNumFoodCans() - Integer.parseInt(sentFood.getText().toString());
            int newClothesNeeds = locationNeeds.getNumClothing() - Integer.parseInt(sentClothes.getText().toString());
            int newWaterNeeds = locationNeeds.getNumWaterBottles() - Integer.parseInt(sentWater.getText().toString());

            db.disasterLocationsDAO().updateLocation(new DisasterLocation(locationNeeds.getCity(),
                    locationNeeds.getState(),
                    locationNeeds.getPostalCode(),
                    newFoodNeeds,
                    newClothesNeeds,
                    newWaterNeeds));
            return locationNeeds;
        }

        @Override
        protected void onPostExecute(DisasterLocation locationNeeds) {
            sentFood.getText().clear();
            sentClothes.getText().clear();
            sentWater.getText().clear();

            neededFood.setText(String.format(Locale.getDefault(), "%d", locationNeeds.getNumFoodCans()));
            neededClothes.setText(String.format(Locale.getDefault(), "%d", locationNeeds.getNumClothing()));
            neededWater.setText(String.format(Locale.getDefault(), "%d", locationNeeds.getNumWaterBottles()));
        }
    }

    private class LocationAdapter extends ArrayAdapter<DisasterLocation> {
        public LocationAdapter(DisasterLocation[] locations) {
            super(CenterHomeActivity.this, 0, locations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(null == convertView) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_disaster_location, null);
            }

            DisasterLocation location = getItem(position);

            TextView cityName = convertView.findViewById(R.id.city_name);
            cityName.setText(location.getCity());

            TextView stateName = convertView.findViewById(R.id.state_name);
            stateName.setText(location.getState());

            return convertView;
        }

        @Nullable
        @Override
        public DisasterLocation getItem(int position) {
            return super.getItem(position);
        }
    }
}
