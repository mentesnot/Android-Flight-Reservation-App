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
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.mentesnot.easytravel.database.DatabaseHelper;
import com.mentesnot.easytravel.HelperUtils.HelperUtilities;
import com.mentesnot.easytravel.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class CheckoutOnewayFragment extends Fragment {

    private ListView selectedFlight;
    private SharedPreferences sharedPreferences;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Bundle bundle;
    private int flightID;
    private android.support.v7.app.ActionBar actionBar;
    private Button btnSelectFlight;
    private int currentTab;
    private Intent intent;

    private TextView flightNo;
    private TextView origin;
    private TextView destination;
    private TextView departureDate;
    private TextView arrivalDate;
    private TextView departureTime;
    private TextView arrivalTime;
    private TextView flightDuration;
    private TextView flightClass;
    private TextView airline;
    private TextView fare;
    private TextView totalFare;
    private Button bookFlight;
    private int clientID;
    private boolean flightExists = false;
    private int numTraveller;
    private TextView numTrav;
    private double onewayFare;


    public CheckoutOnewayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checkout_oneway, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        flightID = sharedPreferences.getInt("ONEWAY_FLIGHT_ID", 0);
        numTraveller = sharedPreferences.getInt("ONEWAY_NUM_TRAVELLER", 0);

        clientID = clientID();

        airline = (TextView) view.findViewById(R.id.txtCheckoutAirline);
        flightNo = (TextView) view.findViewById(R.id.txtCheckoutFlightNumber);
        origin = (TextView) view.findViewById(R.id.txtCheckoutOrigin);
        destination = (TextView) view.findViewById(R.id.txtCheckoutDestination);
        departureDate = (TextView) view.findViewById(R.id.txtCheckoutDepartureDate);
        arrivalDate = (TextView) view.findViewById(R.id.txtCheckoutArrivalDate);
        departureTime = (TextView) view.findViewById(R.id.txtCheckoutDepartureTime);
        arrivalTime = (TextView) view.findViewById(R.id.txtCheckoutArrivalTime);
        flightDuration = (TextView) view.findViewById(R.id.txtCheckoutFlightDuration);
        flightClass = (TextView) view.findViewById(R.id.txtCheckoutFlightClass);
        numTrav = (TextView) view.findViewById(R.id.txtCheckoutTraveller);
        fare = (TextView) view.findViewById(R.id.txtCheckoutFare);
        totalFare = (TextView) view.findViewById(R.id.txtCheckoutTotalFare);

        bookFlight = (Button)view.findViewById(R.id.btnOneWayCheckout) ;

        bookFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookFlight(clientID);
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        displaySelectedFlightInfo(flightID);
        fare.setText("$" + onewayFare);
        totalFare.setText(String.valueOf(HelperUtilities.calculateTotalFare(onewayFare,numTraveller)));
    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(db, id);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                //Toast.makeText(getActivity().getApplicationContext(), String.valueOf(cursor.getCount()), Toast.LENGTH_SHORT).show();
                flightNo.setText(String.valueOf(cursor.getInt(1)));
                origin.setText(cursor.getString(2));
                destination.setText(cursor.getString(3));
                departureDate.setText(cursor.getString(4));
                arrivalDate.setText(cursor.getString(5));
                departureTime.setText(cursor.getString(6));
                arrivalTime.setText(cursor.getString(7));
                flightDuration.setText(cursor.getString(8) + "h");
                onewayFare = cursor.getDouble(9);
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));

                numTrav.setText(String.valueOf(numTraveller));
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Database error occurred", Toast.LENGTH_SHORT).show();
        }
    }

    public void bookFlight(int clientID){
        try{

            databaseHelper = new DatabaseHelper(getActivity());
            db = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectItinerary(db, flightID, clientID);

            if(cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();

					flightExists = true;

					flightAlreadyBookedAlert().show();

					//Toast.makeText(getActivity().getApplicationContext(), "You already booked this flight.", Toast.LENGTH_SHORT).show();
            }else{
				
				flightExists = false;


				DatabaseHelper.insertItinerary(db, flightID, clientID, numTraveller);

                bookFlightDialog().show();
				//Toast.makeText(getActivity().getApplicationContext(), "Your flight booked successfully.", Toast.LENGTH_SHORT).show();
			}

           

        }catch(SQLiteException e){

        }
    }

    public Dialog bookFlightDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Your flight booked successfully. ")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }

    public Dialog flightAlreadyBookedAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You already booked this flight. ")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }


    public int clientID() {
        LoginActivity.sharedPreferences = getActivity().getSharedPreferences(LoginActivity.MY_PREFERENCES, Context.MODE_PRIVATE);
        clientID = LoginActivity.sharedPreferences.getInt(LoginActivity.CLIENT_ID, 0);
        return clientID;
    }
}
