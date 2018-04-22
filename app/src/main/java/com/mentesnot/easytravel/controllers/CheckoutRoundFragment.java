/*************************************************************************************************
 * JANUARY 8, 2018
 *  Mentesnot Aboset
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
public class CheckoutRoundFragment extends Fragment {

    private ListView selectedFlight;
    private SharedPreferences sharedPreferences;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private Bundle bundle;
    private int outboundFlightID;
    private int returnFlightID;
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

    private TextView flightNoReturn;
    private TextView originReturn;
    private TextView destinationReturn;
    private TextView departureDateReturn;
    private TextView arrivalDateReturn;
    private TextView departureTimeReturn;
    private TextView arrivalTimeReturn;
    private TextView flightDurationReturn;
    private TextView flightClassReturn;
    private TextView airlineReturn;


    private Button bookFlight;
    private int clientID;
    private boolean flightExists = false;
    private View view;
    private int numTraveller;

    private double outboundFare;
    private double returnFare;
    private double totalCost;
    private TextView fareRet;
    private TextView traveller;


    public CheckoutRoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_checkout_round, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        outboundFlightID = sharedPreferences.getInt("OUTBOUND_FLIGHT_ID", 0);
        returnFlightID = sharedPreferences.getInt("RETURN_FLIGHT_ID", 0);
        numTraveller = sharedPreferences.getInt("ROUND_NUM_TRAVELLER", 0);


        clientID = clientID();

        airline = (TextView) view.findViewById(R.id.txtCheckoutAirlineOutbound);
        flightNo = (TextView) view.findViewById(R.id.txtCheckoutFlightNumberOutbound);
        origin = (TextView) view.findViewById(R.id.txtCheckoutOriginOutbound);
        destination = (TextView) view.findViewById(R.id.txtCheckoutDestinationOutbound);
        departureDate = (TextView) view.findViewById(R.id.txtCheckoutDepartureDateOutbound);
        arrivalDate = (TextView) view.findViewById(R.id.txtCheckoutArrivalDateOutbound);
        departureTime = (TextView) view.findViewById(R.id.txtCheckoutDepartureTimeOutbound);
        arrivalTime = (TextView) view.findViewById(R.id.txtCheckoutArrivalTimeOutbound);
        flightDuration = (TextView) view.findViewById(R.id.txtCheckoutFlightDurationOutbound);
        flightClass = (TextView) view.findViewById(R.id.txtCheckoutFlightClassOutbound);


        airlineReturn = (TextView) view.findViewById(R.id.txtCheckoutAirlineReturn);
        flightNoReturn = (TextView) view.findViewById(R.id.txtCheckoutFlightNumberReturn);
        originReturn = (TextView) view.findViewById(R.id.txtCheckoutOriginReturn);
        destinationReturn = (TextView) view.findViewById(R.id.txtCheckoutDestinationReturn);
        departureDateReturn = (TextView) view.findViewById(R.id.txtCheckoutDepartureDateReturn);
        arrivalDateReturn = (TextView) view.findViewById(R.id.txtCheckoutArrivalDateReturn);
        departureTimeReturn = (TextView) view.findViewById(R.id.txtCheckoutDepartureTimeReturn);
        arrivalTimeReturn = (TextView) view.findViewById(R.id.txtCheckoutArrivalTimeReturn);
        flightDurationReturn = (TextView) view.findViewById(R.id.txtCheckoutFlightDurationReturn);
        flightClassReturn = (TextView) view.findViewById(R.id.txtCheckoutFlightClassReturn);


        traveller = (TextView) view.findViewById(R.id.txtCheckoutTravellerOutbound);
        fareRet = (TextView) view.findViewById(R.id.txtCheckoutFareReturn);
        fare = (TextView) view.findViewById(R.id.txtCheckoutFareOutbound);
        totalFare = (TextView) view.findViewById(R.id.txtCheckoutTotalFareOutbound);


        bookFlight = (Button) view.findViewById(R.id.btnRoundCheckout);
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
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        displaySelectedOutboundFlightInfo(outboundFlightID);
        displaySelectedReturnFlightInfo(returnFlightID);

        fare.setText("$" + outboundFare);
        fareRet.setText("$" + returnFare);
        traveller.setText(String.valueOf(numTraveller));

        totalCost = HelperUtilities.calculateTotalFare(outboundFare, returnFare, numTraveller);
        totalFare.setText("$" + totalCost);
    }

    public void displaySelectedOutboundFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(db, id);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();


                flightNo.setText(String.valueOf(cursor.getInt(1)));
                origin.setText(cursor.getString(2));
                destination.setText(cursor.getString(3));
                departureDate.setText(cursor.getString(4));
                arrivalDate.setText(cursor.getString(5));
                departureTime.setText(cursor.getString(6));
                arrivalTime.setText(cursor.getString(7));
                flightDuration.setText(cursor.getString(8) + "h");
                outboundFare = cursor.getDouble(9);
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Database error occurred 1", Toast.LENGTH_SHORT).show();
        }
    }

    public void displaySelectedReturnFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(db, id);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();

                flightNoReturn.setText(cursor.getString(1));
                originReturn.setText(cursor.getString(2));
                destinationReturn.setText(cursor.getString(3));
                departureDateReturn.setText(cursor.getString(4));
                arrivalDateReturn.setText(cursor.getString(5));
                departureTimeReturn.setText(cursor.getString(6));
                arrivalTimeReturn.setText(cursor.getString(7));
                flightDurationReturn.setText(cursor.getString(8));
                returnFare = cursor.getDouble(9);
                airlineReturn.setText(cursor.getString(10));
                flightClassReturn.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {
            Toast.makeText(getActivity().getApplicationContext(), "Database error occurred 2", Toast.LENGTH_SHORT).show();
        }
    }

    public void bookFlight(int clientID) {
        try {

            databaseHelper = new DatabaseHelper(getActivity());
            db = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectItinerary(db, outboundFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {

                flightExists = true;

            }

            cursor = DatabaseHelper.selectItinerary(db, returnFlightID, clientID);

            if (cursor != null && cursor.getCount() > 0) {
                flightExists = true;

            }
            if (flightExists) {
                roundFlightAlreadyBookedAlert().show();
            }

            if (flightExists == false) {

                DatabaseHelper.insertItinerary(db, outboundFlightID, clientID, numTraveller);
                DatabaseHelper.insertItinerary(db, returnFlightID, clientID, numTraveller);

                bookFlightDialog().show();

            }

        } catch (SQLiteException e) {

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

    public Dialog roundFlightAlreadyBookedAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("You already booked either the outbound or the return flight. Please select another flight and try again. ")
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
