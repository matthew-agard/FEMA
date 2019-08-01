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
import java.util.Arrays;
import java.util.Map;

public class CenterHomeActivity extends AppCompatActivity {
    private Bundle extras;
    private ListView loadLocations;
    private LocationsAdapter adapter;
    private String selectedLocation = "", centerName, centerUsername;
    private String[] locations;
    private TextView neededFood, neededClothes, neededWater;
    private EditText sentFood, sentClothes, sentWater;
    private Button submitDonations, logout;
    private FirebaseDatabase database;
    private DatabaseReference getLocations, updateInventory, chosenLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_home_page);
        setTitle("Center Home Page");

        database = FirebaseDatabase.getInstance();
        neededFood = findViewById(R.id.food_needed);
        neededClothes = findViewById(R.id.clothes_needed);
        neededWater = findViewById(R.id.water_needed);
        sentFood = findViewById(R.id.food_contributed);
        sentClothes = findViewById(R.id.clothes_contributed);
        sentWater = findViewById(R.id.water_contributed);
        extras = getIntent().getExtras();
        centerUsername = extras.getString("Username");
        centerName = extras.getString("Center Name");

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

        submitDonations = findViewById(R.id.submit_donations);
        submitDonations.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deductDisasterNeed();
                fulfillDonation();
                deductCenterInventory();

                Toast.makeText(CenterHomeActivity.this, "Thank you for your generosity!", Toast.LENGTH_SHORT).show();
//                sentFood.getText().clear();
//                sentClothes.getText().clear();
//                sentWater.getText().clear();
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
        DatabaseReference chosenLocation = database.getReference("Disaster Locations/" + selectedLocation);
        chosenLocation.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> location = (Map) dataSnapshot.getValue();
                if(location != null) {
                    neededFood.setText(String.valueOf(location.get("neededFood")));
                    neededClothes.setText(String.valueOf(location.get("neededClothes")));
                    neededWater.setText(String.valueOf(location.get("neededWater")));
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("CenterHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private void deductDisasterNeed() {
        chosenLocation = database.getReference("Disaster Locations").child(selectedLocation);
        chosenLocation.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> location = (Map) dataSnapshot.getValue();
                if(location != null) {
                    long updatedFood = location.get("neededFood") - Long.parseLong(sentFood.getText().toString());
                    long updatedClothes = location.get("neededClothes") - Long.parseLong(sentClothes.getText().toString());
                    long updatedWater = location.get("neededWater") - Long.parseLong(sentWater.getText().toString());

                    chosenLocation.child("neededFood").setValue(updatedFood);
                    chosenLocation.child("neededClothes").setValue(updatedClothes);
                    chosenLocation.child("neededWater").setValue(updatedWater);
                }
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("CenterHomeActivity.java", "Failed to read value.", error.toException());
            }
        });
    }

    private void fulfillDonation() {
        DatabaseReference createDonation = database.getReference("Donations").child(selectedLocation).child(centerName);
        createDonation.child("donatedFood").setValue(Long.parseLong(sentFood.getText().toString()));
        createDonation.child("donatedClothes").setValue(Long.parseLong(sentClothes.getText().toString()));
        createDonation.child("donatedWater").setValue(Long.parseLong(sentWater.getText().toString()));
    }

    private void deductCenterInventory() {
        updateInventory = database.getReference("Community Centers").child(centerUsername);
        updateInventory.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Map<String, Long> center = (Map) dataSnapshot.getValue();
                if(center != null) {
                    long updatedFood = center.get("inventoryFood") - Long.parseLong(sentFood.getText().toString());
                    long updatedClothes = center.get("inventoryClothes") - Long.parseLong(sentClothes.getText().toString());
                    long updatedWater = center.get("inventoryWater") - Long.parseLong(sentWater.getText().toString());

                    updateInventory.child("inventoryFood").setValue(updatedFood);
                    updateInventory.child("inventoryClothes").setValue(updatedClothes);
                    updateInventory.child("inventoryWater").setValue(updatedWater);
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
