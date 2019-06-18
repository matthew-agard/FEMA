package com.appdev.matthewa.fema;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class CenterHomeActivity extends AppCompatActivity {
    private ListView loadLocations;
    private LocationsAdapter adapter;
    private String selectedLocation = "";
    private String[] locations;
    private TextView neededFood, neededClothes, neededWater;
    private EditText sentFood, sentClothes, sentWater;
    private Button submitDonations, logout;
    private FirebaseDatabase database;
    private DatabaseReference getLocations, chosenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_home_page);
        setTitle("Center Home Page");

        database = FirebaseDatabase.getInstance();
        neededFood = findViewById(R.id.food_needed);
        neededClothes = findViewById(R.id.clothes_needed);
        neededWater = findViewById(R.id.water_needed);
        loadLocations = findViewById(R.id.show_all_locations);
        getLocations = database.getReference("Disaster Locations");

        populateLocationsList();

        loadLocations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadLocations.setBackgroundColor(getResources().getColor(android.R.color.background_light));
        loadLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selectedLocation.equals(adapter.getItem(position))) {
                    selectedLocation = adapter.getItem(position);
                    loadLocations.setBackgroundColor(getResources().getColor(android.R.color.background_light));
//                    parent.setBackgroundColor(getResources().getColor(android.R.color.background_light));
//                    view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
//                    loadLocations.setItemChecked(position, true);
                    displayLocationNeeds();
                }

                else {
                    loadLocations.setItemChecked(position, false);
                    selectedLocation = "";
                    neededFood.setText(null);
                    neededClothes.setText(null);
                    neededWater.setText(null);
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

    private void populateLocationsList() {
        getLocations.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> values = (Map) dataSnapshot.getValue();
                if(values != null) {
                    Object[] valueObjects = values.keySet().toArray();
                    locations = Arrays.copyOf(valueObjects, valueObjects.length, String[].class);
                    adapter = new LocationsAdapter(locations);
                    loadLocations.setAdapter(adapter);
                }

                else {
                    Toast.makeText(CenterHomeActivity.this, "No locations in need", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("CenterHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private void displayLocationNeeds() {
        chosenLocation = database.getReference("Disaster Locations/" + selectedLocation);
        chosenLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> values = (Map) dataSnapshot.getValue();
                if(values != null) {
                    neededFood.setText(String.valueOf(values.get("neededFood").toString()));
                    neededClothes.setText(String.valueOf(values.get("neededClothes").toString()));
                    neededWater.setText(String.valueOf(values.get("neededWater").toString()));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("CenterHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private class LocationsAdapter extends ArrayAdapter<String> {
        private LocationsAdapter(String[] locations) {
            super(CenterHomeActivity.this, 0, locations);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.list_item_disaster_location, null);
            }

            String location = getItem(position);

            TextView cityName = convertView.findViewById(R.id.city_name);
            cityName.setText(location);

            return convertView;
        }

        @Nullable
        @Override
        public String getItem(int position) {
            return super.getItem(position);
        }
    }
}
