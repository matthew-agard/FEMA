package com.appdev.matthewa.fema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.Map;

public class DriverHomeActivity extends AppCompatActivity {
    private ListView loadLocations;
    private LocationsAdapter adapter;
    private String selectedLocation = "";
    private String[] locations;
    private Button confirmLocations, logout;
    private FirebaseDatabase database;
    private DatabaseReference getLocations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_home_page);
        setTitle("Driver Home Page");

        database = FirebaseDatabase.getInstance();
        getLocations = database.getReference("Disaster Locations");
        populateLocationsList();

        loadLocations = findViewById(R.id.show_all_centers);
        loadLocations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selectedLocation.equals(adapter.getItem(position))) {
                    loadLocations.setSelector(android.R.color.darker_gray);
                    selectedLocation = adapter.getItem(position);
                }
                else {
                    loadLocations.setSelector(android.R.color.background_light);
                    selectedLocation = "";
                }
            }
        });

        confirmLocations = findViewById(R.id.confirm_location);
        confirmLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverHomeActivity.this, DriverChooseCentersActivity.class);
                i.putExtra("Disaster Location", selectedLocation);
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
                    Toast.makeText(DriverHomeActivity.this, "No locations in need", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DriverHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private class LocationsAdapter extends ArrayAdapter<String> {
        private LocationsAdapter(String[] locations) {
            super(DriverHomeActivity.this, 0, locations);
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