package com.appdev.matthewa.fema;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class DriverHomeActivity extends AppCompatActivity {
    private ListView loadLocations;
    private LocationAdapter adapter;
    private ArrayList<DisasterLocation> selectedLocations = new ArrayList();
    private DisasterLocation[] loadedLocations;
    private Button confirmLocations, logout;
    private FEMADatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_home_page);
        setTitle("Driver Home Page");

        db = FEMADatabase.getDatabase(this);

        loadLocations = findViewById(R.id.show_all_disaster_locations);
        new RetrieveAllLocations().execute();

        loadLocations.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        loadLocations.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        loadLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                DisasterLocation chosenLocation = adapter.getItem(position);

                if (selectedLocations.contains(chosenLocation)) {
                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                    selectedLocations.remove(chosenLocation);
                }

                else {
                    if((selectedLocations.size() + 1) <= 3) {
                        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        selectedLocations.add(chosenLocation);
                    }

                    else
                        Toast.makeText(DriverHomeActivity.this, "Deselect an existing location selection to add a new location", Toast.LENGTH_LONG).show();
                }
            }
        });

        confirmLocations = findViewById(R.id.confirm_locations);
        confirmLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverHomeActivity.this, DriverMapsActivity.class);
                startActivity(i);
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
            loadedLocations = db.disasterLocationsDAO().getAllLocations();
            return loadedLocations;
        }

        @Override
        protected void onPostExecute(DisasterLocation[] topVotes) {
            if(loadedLocations.length > 0) {
                adapter = new LocationAdapter(loadedLocations);
                loadLocations.setAdapter(adapter);
            }
            else {
                Toast.makeText(DriverHomeActivity.this, "There are currently no cities in need", Toast.LENGTH_LONG).show();
            }
        }
    }

    private class LocationAdapter extends ArrayAdapter<DisasterLocation> {
        public LocationAdapter(DisasterLocation[] locations) {
            super(DriverHomeActivity.this, 0, locations);
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