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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;

public class DriverChooseCentersActivity extends AppCompatActivity {
    private ListView loadCenters;
    private LocationsAdapter adapter;
    private ArrayList<String> selectedCenters = new ArrayList<>();
    private String selectedLocation;
    private String[] centers;
    private Button confirmLocations, logout;
    private FirebaseDatabase database;
    private DatabaseReference getCenters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_choose_centers);
        setTitle("Driver Centers Page");

        database = FirebaseDatabase.getInstance();
        selectedLocation = getIntent().getStringExtra("Disaster Location");
        getCenters = database.getReference("Donations").child(selectedLocation);
        populateCentersLocationsList();

        loadCenters = findViewById(R.id.show_all_centers);
        loadCenters.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String chosenLocation = adapter.getItem(position);

                if (selectedCenters.contains(chosenLocation)) {
                    loadCenters.setSelector(android.R.color.background_light);
                    view.setBackgroundColor(getResources().getColor(android.R.color.background_light));
                    selectedCenters.remove(chosenLocation);
                }

                else {
                    if((selectedCenters.size()) < 3) {
                        loadCenters.setSelector(android.R.color.darker_gray);
                        view.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
                        selectedCenters.add(chosenLocation);
                    }

                    else
                        Toast.makeText(DriverChooseCentersActivity.this, "Deselect an existing location selection to add a new location", Toast.LENGTH_LONG).show();
                }
            }
        });

        confirmLocations = findViewById(R.id.confirm_location);
        confirmLocations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(DriverChooseCentersActivity.this, DriverMapsActivity.class);
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

    private void populateCentersLocationsList() {
        getCenters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Object> values = (Map) dataSnapshot.getValue();
                if(values != null) {
                    Object[] valueObjects = values.keySet().toArray();
                    centers = Arrays.copyOf(valueObjects, valueObjects.length, String[].class);
                    adapter = new LocationsAdapter(centers);
                    loadCenters.setAdapter(adapter);
                }
                else {
                    Toast.makeText(DriverChooseCentersActivity.this, "No center locations listed for this disaster location", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("DriverChooseCentersAct", "Failed to read value.", error.toException());
            }
        });
    }

    private class LocationsAdapter extends ArrayAdapter<String> {
        private LocationsAdapter(String[] centers) {
            super(DriverChooseCentersActivity.this, 0, centers);
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
