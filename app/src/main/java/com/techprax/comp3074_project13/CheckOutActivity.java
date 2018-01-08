/*************************************************************************************************
 * JANUARY 8, 2018
 * COMP3074 - PROJECT 13
 * Members:
 *           HAMAD AHMAD:       101006399
 *           MENTESNOT ABOSET : 101022050
 *           TOAN NGUYEN:       100979753
 *           ZHENG LIU:         100970328
 * ************************************************************************************************/
package com.techprax.comp3074_project13;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

public class CheckOutActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private int currentTab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        currentTab = sharedPreferences.getInt("CURRENT_TAB", 0);

        //Toast.makeText(getApplicationContext(), String.valueOf(currentTab), Toast.LENGTH_SHORT).show();


        if (currentTab == 0) {
            // Create new fragment and transaction
            CheckoutOnewayFragment newFragment = new CheckoutOnewayFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        } else if (currentTab == 1) {
            // Create new fragment and transaction
            CheckoutRoundFragment newFragment = new CheckoutRoundFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();

            // Replace whatever is in the fragment_container view with this fragment,
            // and add the transaction to the back stack
            transaction.replace(R.id.fragment_container, newFragment);
            transaction.addToBackStack(null);

            // Commit the transaction
            transaction.commit();
        }


    }


}
