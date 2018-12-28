package com.appdev.matthewa.fema;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {
    private Spinner userSpinner;
    private EditText email, password;
    private Button login;
    private TextView createAccount;
    private int userTypePosition;
    private boolean validLogin;
    private FEMADatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setTitle("Login");

        db = FEMADatabase.getDatabase(this);

        userSpinner = findViewById(R.id.user_type);
        final ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.user_type, android.R.layout.simple_spinner_dropdown_item);
        userSpinner.setAdapter(adapter);
        userSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                userTypePosition = position;
                verifyAccountCreation(userTypePosition);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                verifyAccountCreation(0);
            }
        });

        email = findViewById(R.id.user_username);
        password = findViewById(R.id.user_password);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(email.getText().toString(), password.getText().toString());
            }
        });

        createAccount = findViewById(R.id.create_account);
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createUserAccount();
            }
        });
    }

    private class AgentLoginTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            User user = db.userDAO().findAgentLogin(users[0].getEmail(), users[0].getPassword());

            if(user == null)
                validLogin = false;
            else {
                validLogin = true;
            }

            return validLogin;
        }

        @Override
        protected void onPostExecute(Boolean validLogin) {
            if (validLogin) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                String customerUsername = email.getText().toString();
                email.getText().clear();
                password.getText().clear();
                Intent i = new Intent(LoginActivity.this, AgentHomeActivity.class);
                i.putExtra("Username", customerUsername);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private class CenterLoginTask extends AsyncTask<Center, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Center... centers) {
            Center center = db.centerDAO().findCenterLogin(centers[0].getEmail(), centers[0].getPassword());

            if(center == null)
                validLogin = false;
            else
                validLogin = true;

            return validLogin;
        }

        @Override
        protected void onPostExecute(Boolean validLogin) {
            if (validLogin) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                email.getText().clear();
                password.getText().clear();
                Intent i = new Intent(LoginActivity.this, CenterHomeActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private class DriverLoginTask extends AsyncTask<User, Void, Boolean> {
        @Override
        protected Boolean doInBackground(User... users) {
            User user = db.userDAO().findDriverLogin(users[0].getEmail(), users[0].getPassword());

            if(user == null)
                validLogin = false;
            else
                validLogin = true;

            return validLogin;
        }

        @Override
        protected void onPostExecute(Boolean validLogin) {
            if (validLogin) {
                Toast.makeText(LoginActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                email.getText().clear();
                password.getText().clear();
                Intent i = new Intent(LoginActivity.this, DriverHomeActivity.class);
                startActivity(i);
            }
            else
                Toast.makeText(LoginActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
        }
    }

    private void verifyAccountCreation(int userTypePosition) {
        if(userTypePosition != 2) {
            createAccount.setVisibility(View.VISIBLE);
            createAccount.setClickable(true);
        }

        else {
            createAccount.setVisibility(View.INVISIBLE);
            createAccount.setClickable(false);
        }
    }

    private void createUserAccount() {
        email.getText().clear();
        password.getText().clear();

        if(userTypePosition == 0) {
            Intent i = new Intent(LoginActivity.this, CenterCreateAccountActivity.class);
            startActivity(i);
        }
        else {
            Intent i = new Intent(LoginActivity.this, DriverCreateAccountActivity.class);
            startActivity(i);
        }
    }

    private void signInUser(String email, String password) {
        if(userTypePosition == 0)
            new AgentLoginTask().execute(new User(email, password));

        else if (userTypePosition == 1)
            new CenterLoginTask().execute(new Center(email, password));

        else
            new DriverLoginTask().execute(new User(email, password));
    }
}