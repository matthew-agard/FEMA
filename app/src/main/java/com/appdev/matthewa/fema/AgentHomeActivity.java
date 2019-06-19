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
import android.widget.EditText;
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

public class AgentHomeActivity extends AppCompatActivity {
    private ListView loadLocations;
    private LocationsAdapter adapter;
    private String selectedLocation = "";
    private String[] locations;
    private TextView neededFood, neededClothes, neededWater;
    private EditText updatedFood, updatedClothes, updatedWater;
    private FirebaseDatabase database;
    private DatabaseReference chosenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agent_home_page);
        setTitle("Agent Home Page");

        database = FirebaseDatabase.getInstance();
        neededFood = findViewById(R.id.food_needed);
        neededClothes = findViewById(R.id.clothes_needed);
        neededWater = findViewById(R.id.water_needed);
        updatedFood = findViewById(R.id.food_contributed);
        updatedClothes = findViewById(R.id.clothes_contributed);
        updatedWater = findViewById(R.id.water_contributed);

        loadLocations = findViewById(R.id.show_all_locations);
        populateLocationsList();

        loadLocations.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        loadLocations.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!selectedLocation.equals(adapter.getItem(position))) {
                    loadLocations.setSelector(android.R.color.darker_gray);
                    selectedLocation = adapter.getItem(position);
                    displayLocationNeeds();
                }
                else {
                    loadLocations.setSelector(android.R.color.background_light);
                    selectedLocation = "";
                    neededFood.setText(null);
                    neededClothes.setText(null);
                    neededWater.setText(null);
                }
            }
        });

        TextView addLocation = findViewById(R.id.add_location_text);
        addLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AgentHomeActivity.this, AgentAddLocationActivity.class);
                startActivity(i);
            }
        });

        Button updateNeeds = findViewById(R.id.submit_donations);
        updateNeeds.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateDisasterNeed();
                updatedFood.getText().clear();
                updatedClothes.getText().clear();
                updatedWater.getText().clear();
            }
        });

        Button logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void populateLocationsList() {
        DatabaseReference getLocations = database.getReference("Disaster Locations");
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
                    Toast.makeText(AgentHomeActivity.this, "No locations in need", Toast.LENGTH_SHORT).show();
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
                    neededFood.setText(String.valueOf(values.get("neededFood")));
                    neededClothes.setText(String.valueOf(values.get("neededClothes")));
                    neededWater.setText(String.valueOf(values.get("neededWater")));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("AgentHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private void updateDisasterNeed() {
        chosenLocation = database.getReference("Disaster Locations").child(selectedLocation);
        chosenLocation.child("neededFood").setValue(Long.parseLong(updatedFood.getText().toString()));
        chosenLocation.child("neededClothes").setValue(Long.parseLong(updatedClothes.getText().toString()));
        chosenLocation.child("neededWater").setValue(Long.parseLong(updatedWater.getText().toString()));
    }

    private class LocationsAdapter extends ArrayAdapter<String> {
        private LocationsAdapter(String[] locations) {
            super(AgentHomeActivity.this, 0, locations);
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
