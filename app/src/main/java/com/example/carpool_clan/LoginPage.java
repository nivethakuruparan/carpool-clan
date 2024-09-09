package com.example.carpool_clan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class LoginPage extends AppCompatActivity {

    AccountManagementController accountManagement = new AccountManagementController();
    CustomerDatabase db = new CustomerDatabase(LoginPage.this);
    EditText loginEmail, loginPassword;
    Button loginButton;
    TextView registrationPageRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_page);

        loginEmail = findViewById(R.id.login_email);
        loginPassword = findViewById(R.id.login_password);
        loginButton = findViewById(R.id.login_button);
        registrationPageRedirect = findViewById(R.id.registration_page_redirect);

        loginButton.setOnClickListener(view -> {
            boolean notEmptyFields = accountManagement.checkEmptyFields(loginEmail) & accountManagement.checkEmptyFields(loginPassword);
            if (notEmptyFields) { // make sure fields are not empty
                boolean validCustomer = db.verifyCustomer(loginEmail.getText().toString(), loginPassword.getText().toString());
                if (validCustomer) {
                    Toast.makeText(LoginPage.this, "Successfully logged in...", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(LoginPage.this, HomePage.class);
                    startActivity(intent);
                } else {
                    loginEmail.setError("Invalid credentials");
                    loginPassword.setError("Invalid credentials");
                }
            }
        });

        registrationPageRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(LoginPage.this, RegistrationPage.class);
            startActivity(intent);
        });
    }
}