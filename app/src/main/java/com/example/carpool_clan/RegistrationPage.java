package com.example.carpool_clan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class RegistrationPage extends AppCompatActivity {

    AccountManagementController accountManagement = new AccountManagementController();
    CustomerDatabase db = new CustomerDatabase(RegistrationPage.this);
    EditText registrationEmail, registrationPassword, registrationName;
    DatePickerDialog datePickerDialog;
    Button registrationDOB, registrationButton;
    TextView loginPageRedirect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration_page);

        initDatePicker(); // show date picker for date of birth selection

        registrationEmail = findViewById(R.id.registration_email);
        registrationPassword = findViewById(R.id.registration_password);
        registrationName = findViewById(R.id.registration_name);
        registrationDOB = findViewById(R.id.registration_dob);
        registrationButton = findViewById(R.id.registration_button);
        loginPageRedirect = findViewById(R.id.login_page_redirect);

        registrationButton.setOnClickListener(view -> {
            boolean notEmptyFields = accountManagement.checkEmptyFields(registrationName) & accountManagement.checkEmptyFields(registrationEmail) & accountManagement.checkEmptyFields(registrationDOB) & accountManagement.checkEmptyFields(registrationPassword);
            if (notEmptyFields) { // make sure fields are not empty
                boolean validRegistration = accountManagement.validateRegistration(registrationEmail, registrationDOB, registrationPassword);
                if (validRegistration) { // make sure field contents are valid
                    boolean emailExists = db.emailExists(registrationEmail.getText().toString());
                    if (emailExists) { // make sure email doesn't already exist
                        registrationEmail.setError("Email already exists! Please use a different email or try logging in if you have an account.");
                    } else {
                        boolean rc = db.addCustomer(registrationEmail.getText().toString(), registrationPassword.getText().toString(), registrationName.getText().toString(), registrationDOB.getText().toString());
                        if (!rc) {
                            Toast.makeText(RegistrationPage.this, "Something went wrong with adding account to database. Please try again later.", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(RegistrationPage.this, "Successfully created account. Redirecting to login page...", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
                            startActivity(intent);
                        }
                    }
                }
            }
        });

        loginPageRedirect.setOnClickListener(view -> {
            Intent intent = new Intent(RegistrationPage.this, LoginPage.class);
            startActivity(intent);
        });
    }

    private void initDatePicker() {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            Calendar calendar = Calendar.getInstance();
            calendar.set(year, month, day);

            // format the date using SimpleDateFormat
            String date = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault()).format(calendar.getTime());
            registrationDOB.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        datePickerDialog = new DatePickerDialog(this, dateSetListener, year, month, day);
    }

    public void openDatePicker(View view) {
        datePickerDialog.show();  // make sure datePickerDialog is initialized
    }

}