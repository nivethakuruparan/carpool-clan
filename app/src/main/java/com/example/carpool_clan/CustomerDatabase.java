package com.example.carpool_clan;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class CustomerDatabase extends SQLiteOpenHelper {

    private Context context;
    private static final String CUSTOMER_DATABASE_NAME = "CustomerDatabase.db";
    private static final int CUSTOMER_DATABASE_VERSION = 1;
    private static final String CUSTOMER_TABLE_NAME = "customer";
    private static final String COLUMN_EMAIL = "customer_email";
    private static final String COLUMN_PASSWORD = "customer_password";
    private static final String COLUMN_NAME = "customer_name";
    private static final String COLUMN_DOB = "customer_dob";


    public CustomerDatabase(@Nullable Context context) {
        super(context, CUSTOMER_DATABASE_NAME, null, CUSTOMER_DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query =
                "CREATE TABLE " + CUSTOMER_TABLE_NAME +
                        " (" + COLUMN_EMAIL + " TEXT PRIMARY KEY, " +
                        COLUMN_PASSWORD + " TEXT, " +
                        COLUMN_NAME + " TEXT, " +
                        COLUMN_DOB + " TEXT);";

        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS " + CUSTOMER_TABLE_NAME);
        onCreate(db);
    }

    public Boolean addCustomer(String email, String password, String name, String dob) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COLUMN_EMAIL, email);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_DOB, dob);

        long result = db.insert(CUSTOMER_TABLE_NAME, null, cv);
        if (result == -1) {
            return false;
        } else {
            return true;
        }
    }

    public Boolean emailExists(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + CUSTOMER_TABLE_NAME + " WHERE " + COLUMN_EMAIL + " = ?", new String[]{email});

        boolean exists = (cursor.getCount() > 0);  // Store the result in a boolean variable
        cursor.close();  // Always close the cursor after use
        return exists;
    }

    public Boolean verifyCustomer(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT 1 FROM " + CUSTOMER_TABLE_NAME +
                        " WHERE " + COLUMN_EMAIL + " = ? AND " +
                        COLUMN_PASSWORD + " = ?",
                new String[]{email, password});

        boolean exists = (cursor.getCount() > 0);  // Check if any matching rows are found
        cursor.close();  // Always close the cursor after use
        return exists;
    }

}
