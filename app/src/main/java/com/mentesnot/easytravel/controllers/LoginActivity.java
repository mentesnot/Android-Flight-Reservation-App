/*************************************************************************************************
 * JANUARY 8, 2018
 * Mentesnot Aboset
 *  ************************************************************************************************/
package com.mentesnot.easytravel.controllers;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.mentesnot.easytravel.database.DatabaseHelper;
import com.mentesnot.easytravel.HelperUtils.HelperUtilities;
import com.mentesnot.easytravel.R;

public class LoginActivity extends AppCompatActivity {

    public static final String MY_PREFERENCES = "MY_PREFS";
    public static final String EMAIL = "EMAIL";
    public static final String CLIENT_ID = "CLIENT_ID";
    public static final String LOGIN_STATUS = "LOGGED_IN";
    public static SharedPreferences sharedPreferences;
    private EditText inputEmail;
    private EditText inputPassword;
    private TextView txtLoginError;
    private boolean isValid;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int accountID;
    private int clientID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        sharedPreferences = getSharedPreferences(MY_PREFERENCES, 0);
        Boolean loggedIn = sharedPreferences.getBoolean(LOGIN_STATUS, false);//login status

        //checks the login status and redirects to the main activity
        if (loggedIn) {
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        Button btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView linkRegister = (TextView) findViewById(R.id.linkRegister);

        inputEmail = (EditText) findViewById(R.id.email);
        inputPassword = (EditText) findViewById(R.id.password);
        txtLoginError = (TextView) findViewById(R.id.txtLoginError);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });
        linkRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //process login
    public void attemptLogin() {

        try {

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            isValid = isValidUserInput();

            //filters the user input

            String filteredEmail = HelperUtilities.filter(inputEmail.getText().toString());
            String filteredPassword = HelperUtilities.filter(inputPassword.getText().toString());

            if (isValid) {

                cursor = DatabaseHelper.login(db, filteredEmail, filteredPassword);

                if (cursor != null && cursor.getCount() == 1) {
                    cursor.moveToFirst();

                    String email = cursor.getString(1);
                    clientID = cursor.getInt(3);

                    //Toast.makeText(getApplicationContext(), "client id " + String.valueOf(clientID), Toast.LENGTH_SHORT).show();

                    sharedPreferences = getSharedPreferences(MY_PREFERENCES, Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();

                    editor.putInt(CLIENT_ID, clientID);
                    editor.putString(EMAIL, email);
                    editor.putBoolean(LOGIN_STATUS, true);

                    editor.commit();

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();

                } else {

                    txtLoginError.setText("Invalid email or password");
                }
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }
    }

    //validate user input
    public boolean isValidUserInput() {
        if (HelperUtilities.isEmptyOrNull(inputEmail.getText().toString())) {
            txtLoginError.setText("Invalid email or password");
            return false;
        }
        if (HelperUtilities.isEmptyOrNull(inputPassword.getText().toString())) {
            txtLoginError.setText("Invalid email or password");
            return false;
        }
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        try {
            if (cursor != null) {
                cursor.close();
            }

            if (db != null) {
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error closing database or cursor", Toast.LENGTH_SHORT).show();
        }

    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle inState) {
        super.onRestoreInstanceState(inState);
    }

}
