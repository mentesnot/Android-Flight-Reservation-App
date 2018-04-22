/*************************************************************************************************
 * JANUARY 8, 2018
 *  Mentesnot Aboset
 * ************************************************************************************************/
package com.mentesnot.easytravel.controllers;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.mentesnot.easytravel.R;

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
