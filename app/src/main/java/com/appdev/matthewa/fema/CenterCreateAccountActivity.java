package com.appdev.matthewa.fema;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class CenterCreateAccountActivity extends AppCompatActivity {

    private EditText name, city, zipcode, username, password, confirmPassword, numFood, numClothing, numWater;
    private Spinner state;
    private String stateSelect;
    private Button createAccount;
    private FEMADatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_create_account);
        setTitle("Create Community Center Account");

        db = FEMADatabase.getDatabase(this);

        name = findViewById(R.id.center_name);
        city = findViewById(R.id.center_city);
        zipcode = findViewById(R.id.center_zipcode);
        username = findViewById(R.id.center_name);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.center_zipcode);
        numFood = findViewById(R.id.center_food);
        numClothing = findViewById(R.id.center_clothing);
        numWater = findViewById(R.id.center_water);

        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.state_select, android.R.layout.simple_spinner_dropdown_item);
        state = findViewById(R.id.center_state);
        state.setAdapter(adapter);
        state.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                stateSelect = (String) adapter.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                stateSelect = (String) adapter.getItem(0);
            }
        });

        createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (true) {
                    Toast.makeText(CenterCreateAccountActivity.this, "Address does not exist", Toast.LENGTH_SHORT).show();
                }
                else if (!password.getText().toString().equals(confirmPassword.getText().toString()))
                    Toast.makeText(CenterCreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
                else
                    createAccount(username.getText().toString(), password.getText().toString());
            }
        });
    }

    private class CreateCenterTask extends AsyncTask<Center, Void, Void> {
        @Override
        protected Void doInBackground(Center... centers) {
            db.centerDAO().insertCenter(centers[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            Toast.makeText(CenterCreateAccountActivity.this, "Center account created", Toast.LENGTH_SHORT).show();
        }
    }

    private void createAccount(String userEmail, String userPassword) {
        new CreateCenterTask().execute(new Center(userEmail, userPassword));
        username.getText().clear();
        password.getText().clear();
        confirmPassword.getText().clear();
        finish();
    }
}

