package com.appdev.matthewa.fema;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private FirebaseAuth mAuth;
    private Spinner userSpinner;
    private EditText username, password;
    private Button login;
    private TextView createAccount;
    private int userTypePosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_page);
        setTitle("Login");

        database = FirebaseDatabase.getInstance();
        mAuth = FirebaseAuth.getInstance();

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

        username = findViewById(R.id.user_username);
        password = findViewById(R.id.user_password);

        login = findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signInUser(username.getText().toString() + "@fema.org", password.getText().toString());
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

    @Override
    protected void onResume() {
        super.onResume();
        username.getText().clear();
        password.getText().clear();
    }

    private void verifyAccountCreation(int userTypePosition) {
        if(userTypePosition == 0) {
            createAccount.setVisibility(View.VISIBLE);
            createAccount.setClickable(true);
        }

        else {
            createAccount.setVisibility(View.INVISIBLE);
            createAccount.setClickable(false);
        }
    }

    private void createUserAccount() {
        if(userTypePosition == 0) {
            Intent i = new Intent(LoginActivity.this, CenterCreateAccountActivity.class);
            startActivity(i);
        }
    }

    private void signInUser(final String usernameStr, String passwordStr) {
        mAuth.signInWithEmailAndPassword(usernameStr, passwordStr).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    if(userTypePosition == 0) {
                        DatabaseReference getCenter = database.getReference("Community Centers").child(username.getText().toString());
                        verifyUser(getCenter);
                    }

                    else {
                        DatabaseReference getAgent = database.getReference("Agents").child(username.getText().toString());
                        verifyUser(getAgent);
                    }
                }

                else {
                    Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void verifyUser(DatabaseReference userReference) {
        userReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Map<String, String> userData = (Map) dataSnapshot.getValue();
                if(userData != null && userData.get("password").equals(password.getText().toString())) {
                    if(userTypePosition == 0) {
                        Intent i = new Intent(LoginActivity.this, CenterHomeActivity.class);
                        Bundle extras = new Bundle();
                        extras.putString("Username", username.getText().toString());
                        extras.putString("Center Name", userData.get("centerName"));
                        i.putExtras(extras);
                        startActivity(i);
                    }
                    else {
                        Intent i = new Intent(LoginActivity.this, AgentHomeActivity.class);
                        startActivity(i);
                    }
                }

                else {
                    Toast.makeText(LoginActivity.this, "Invalid login credentials.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}