package com.example.carpool_clan;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AccountManagementController {
    public Boolean checkEmptyFields(TextView field) {
        // Checks if provided TextView is empty
        String text = field.getText().toString();

        if (text.isEmpty()) {
            field.setError("Field cannot be empty!");
            return false;
        }
        field.setError(null);
        return true;
    }

    public Boolean validateRegistration(EditText email, Button dob, EditText password) {
        return validateEmailFormat(email) & validateDOB(dob) & validatePassword(password);
    }

    private Boolean validateEmailFormat(EditText email) {
        String text = email.getText().toString();
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            email.setError("Please enter a valid e-mail");
            return false;
        }
        email.setError(null);
        return true;
    }

    private Boolean validateDOB(Button dob) {
        String text = dob.getText().toString();
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd yyyy", Locale.getDefault());

        try {
            // Parse the date from the text
            Date dobDate = dateFormat.parse(text);
            Calendar dobCal = Calendar.getInstance();
            dobCal.setTime(dobDate);

            // Get current date
            Calendar today = Calendar.getInstance();

            // Calculate age
            int age = today.get(Calendar.YEAR) - dobCal.get(Calendar.YEAR);

            // Check if birthday hasn't occurred this year yet
            if (today.get(Calendar.DAY_OF_YEAR) < dobCal.get(Calendar.DAY_OF_YEAR)) {
                age--;
            }

            // Validate age (should be at least 14)
            if (age < 14) {
                dob.setError("You must be at least 14 years old to use this app");
                return false;
            }

            // Clear error
            dob.setError(null);
            return true;

        } catch (ParseException e) {
            dob.setError("Please enter a valid date");
            return false;
        }
    }

    private Boolean validatePassword(EditText password) {
        String text = password.getText().toString();
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()\\-\\[\\]{}:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(text);
        if (!matcher.matches()) {
            password.setError("Password must contain at least: one uppercase letter, one lowercase letter, one digit, one symbol and must be 8-20 characters long");
            return false;
        }
        password.setError(null);
        return true;
    }

}
