package com.appdev.matthewa.fema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AgentHomeActivity extends AppCompatActivity {
    private EditText city, state, zipcode, food, water, clothes;
    private Button submitEntry, logout;
    private String customerUsername;
    private FEMADatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Agent Home Page");
        setContentView(R.layout.agent_home_page);

        db = FEMADatabase.getDatabase(this);

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
            public void onClick(View v) {
                new InsertNewLocation().execute(new DisasterLocation(city.getText().toString(),
                        state.getText().toString(),
                        Long.parseLong(zipcode.getText().toString()),
                        Integer.parseInt(food.getText().toString()),
                        Integer.parseInt(clothes.getText().toString()),
                        Integer.parseInt(water.getText().toString())));
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

    private class InsertNewLocation extends AsyncTask<DisasterLocation, Void, Void> {
        @Override
        protected Void doInBackground(DisasterLocation... disasterLocations) {
            db.disasterLocationsDAO().insertLocation(disasterLocations);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            city.getText().clear();
            state.getText().clear();
            zipcode.getText().clear();
            food.getText().clear();
            clothes.getText().clear();
            water.getText().clear();
            Toast.makeText(AgentHomeActivity.this, "Disaster Location Created", Toast.LENGTH_SHORT).show();
        }
    }
}

