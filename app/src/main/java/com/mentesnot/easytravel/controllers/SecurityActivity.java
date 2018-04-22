/*************************************************************************************************
 * JANUARY 8, 2018
 * Mentesnot Aboset
 * ************************************************************************************************/
package com.mentesnot.easytravel.controllers;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class SecurityActivity extends AppCompatActivity {

    private EditText oldPassword;
    private EditText newPassword;
    private EditText confirmPassword;
    private int clientID = 1;
    private TextView txtMatchError;
    private boolean isValid;
    private boolean isValidUser;

    private SQLiteOpenHelper hospitalDatabaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_security);

        oldPassword = (EditText) findViewById(R.id.txtOldPassword);
        newPassword = (EditText) findViewById(R.id.txtNewPassword);
        confirmPassword = (EditText) findViewById(R.id.txtConfirmPassword);
        txtMatchError = (TextView) findViewById(R.id.txtMatchError);

        Button btnChangePassword = (Button) findViewById(R.id.btnChangePassword);


        clientID = clientID();

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                changePassword();
            }
        });

    }

    public void changePassword() {
        try {

            hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
            db = hospitalDatabaseHelper.getWritableDatabase();

            clientID = clientID();
            isValid = isValidInput();
            isValidUser = isValidUser();

            if (isValid && isValidUser) {
                DatabaseHelper.updatePassword(db,
                        newPassword.getText().toString(),
                        String.valueOf(clientID));

                changePasswordDialog().show();
            }
        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }

    public Dialog changePasswordDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The password changed successfully! ")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }


    public boolean isValidInput() {
        isValidUser = isValidUser();
        if (HelperUtilities.isEmptyOrNull(oldPassword.getText().toString())) {
            oldPassword.setError("Please enter your password");
            return false;
        } else if (!isValidUser) {
            oldPassword.setError("Incorrect password");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(newPassword.getText().toString())) {
            newPassword.setError("Please enter your new password");
            return false;
        } else if (HelperUtilities.isEmptyOrNull(confirmPassword.getText().toString())) {
            confirmPassword.setError("Please confirm your new password");
            return false;
        } else if (HelperUtilities.isShortPassword(newPassword.getText().toString())) {
            newPassword.setError("Your password must have at least 6 characters.");
            return false;
        } else if (!newPassword.getText().toString().equals(confirmPassword.getText().toString())) {
            txtMatchError.setText("The password doesn't match");
            return false;
        }
        return true;
    }

    public boolean isValidUser() {
        try {

            hospitalDatabaseHelper = new DatabaseHelper(getApplicationContext());
            db = hospitalDatabaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectClientPassword(db, clientID);


            if (cursor.moveToFirst()) {

                String password = cursor.getString(0);

                if (!oldPassword.getText().toString().equals(password)) {
                    return false;
                } else {
                    return true;
                }

            }


        } catch (SQLiteException ex) {
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;

    }


    public int clientID() {
        LoginActivity.sharedPreferences = getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = LoginActivity.sharedPreferences.getInt(LoginActivity.CLIENT_ID, 0);
        return clientID;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(cursor != null){
                cursor.close();
            }
            if(db != null){
                db.close();
            }
        } catch (Exception ex) {
            Toast.makeText(getApplicationContext(), "Error closing database or cursor", Toast.LENGTH_SHORT).show();
        }
    }

}
