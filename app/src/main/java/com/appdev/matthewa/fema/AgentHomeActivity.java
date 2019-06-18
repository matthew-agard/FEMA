package com.appdev.matthewa.fema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AgentHomeActivity extends AppCompatActivity {
    private EditText city, state, zipcode, food, water, clothes;
    private Button submitEntry, logout;
    private String customerUsername;
    private FirebaseDatabase database;
    private DatabaseReference newLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Agent Home Page");
        setContentView(R.layout.agent_home_page);

        database = FirebaseDatabase.getInstance();

        customerUsername = getIntent().getStringExtra("Username");
        city = findViewById(R.id.city_text);
        state = findViewById(R.id.state_text);
        zipcode = findViewById(R.id.num_zipcode);
        food = findViewById(R.id.num_food);
        water = findViewById(R.id.num_water);
        clothes = findViewById(R.id.num_clothes);
        submitEntry = findViewById(R.id.submit_entry);
        submitEntry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                newLocation = database.getReference("Disaster Locations")
                                .child(city.getText().toString() + ", " + state.getText().toString());
                newLocation.child("neededClothes").setValue(Long.parseLong(clothes.getText().toString()));
                newLocation.child("neededFood").setValue(Long.parseLong(food.getText().toString()));
                newLocation.child("neededWater").setValue(Long.parseLong(water.getText().toString()));
                newLocation.child("zipcode").setValue(Long.parseLong(zipcode.getText().toString()));

                Toast.makeText(AgentHomeActivity.this, "Sucessfully added disaster location", Toast.LENGTH_SHORT).show();

                city.getText().clear();
                state.getText().clear();
                zipcode.getText().clear();
                food.getText().clear();
                water.getText().clear();
                clothes.getText().clear();
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
}

