package com.appdev.matthewa.fema;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class CenterCreateAccountActivity extends AppCompatActivity {

    private EditText name, city, zipcode, username, password, confirmPassword, numFood, numWater, numClothing;
    private Spinner state;
    private String stateSelect;
    private Button createAccount;
    private FirebaseAuth mAuth;
    private FirebaseDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.center_create_account);
        setTitle("Create Community Center Account");

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

        name = findViewById(R.id.center_name);
        city = findViewById(R.id.center_city);
        zipcode = findViewById(R.id.center_zipcode);
        username = findViewById(R.id.center_username);
        password = findViewById(R.id.center_password);
        confirmPassword = findViewById(R.id.password_reenter);
        numFood = findViewById(R.id.center_food);
        numWater = findViewById(R.id.center_clothing);
        numClothing = findViewById(R.id.center_water);

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
                if(password.getText().toString().length() < 6)
                    Toast.makeText(CenterCreateAccountActivity.this, "Password must be at least 6 characters long", Toast.LENGTH_SHORT).show();
                else
                    createAccount(username.getText().toString() + "@fema.org", password.getText().toString());
            }
        });
    }

    private void createAccount(final String centerEmail, String centerPassword) {
        mAuth.createUserWithEmailAndPassword(centerEmail, centerPassword).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    DatabaseReference newCenter = database.getReference("Community Centers").child(username.getText().toString());
                    newCenter.child("centerName").setValue(name.getText().toString());
                    newCenter.child("city").setValue(city.getText().toString());
                    newCenter.child("inventoryFood").setValue(Long.parseLong(numFood.getText().toString()));
                    newCenter.child("inventoryWater").setValue(Long.parseLong(numWater.getText().toString()));
                    newCenter.child("inventoryClothes").setValue(Long.parseLong(numClothing.getText().toString()));
                    newCenter.child("password").setValue(password.getText().toString());
                    newCenter.child("state").setValue(stateSelect);
                    newCenter.child("zipcode").setValue(Long.parseLong(zipcode.getText().toString()));

                    Toast.makeText(CenterCreateAccountActivity.this, "Account creation successful.", Toast.LENGTH_SHORT).show();
                    finish();
                }

                else
                    Toast.makeText(CenterCreateAccountActivity.this, "Account creation failed.", Toast.LENGTH_SHORT).show();
            }
        });
    }
}