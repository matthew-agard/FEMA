package com.appdev.matthewa.fema;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class DriverCreateAccountActivity extends AppCompatActivity {
    private EditText username, password, confirmPassword;
    private Button createAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.driver_create_account);
        setTitle("Create Truck Driver Account");

        username = findViewById(R.id.center_name);
        password = findViewById(R.id.password);
        confirmPassword = findViewById(R.id.center_zipcode);

        createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals(confirmPassword.getText().toString())) {
                    createAccount(username.getText().toString(), password.getText().toString());
                }

                else
                    Toast.makeText(DriverCreateAccountActivity.this, "Passwords do not match", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void createAccount(String userEmail, String userPassword) {
        username.getText().clear();
        password.getText().clear();
        confirmPassword.getText().clear();
        finish();
    }
}
