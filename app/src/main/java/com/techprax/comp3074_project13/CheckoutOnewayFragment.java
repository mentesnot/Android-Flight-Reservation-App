package com.techprax.comp3074_project13;


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


    public CheckoutOnewayFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checkout_oneway, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        flightID = sharedPreferences.getInt("ONEWAY_FLIGHT_ID", 0);

        clientID = 1;

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
        fare = (TextView) view.findViewById(R.id.txtCheckoutFare);
        totalFare = (TextView) view.findViewById(R.id.txtCheckoutTotalFare);

        bookFlight = (Button)view.findViewById(R.id.btnOneWayCheckout) ;

        bookFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                bookFlight(clientID);
                intent = new Intent(getActivity(), MainActivity.class);
                startActivity(intent);

            }
        });


        // Inflate the layout for this fragment
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);

        displaySelectedFlightInfo(flightID);
    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlightWithID(db, id);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                flightNo.setText(cursor.getString(1));
                origin.setText(cursor.getString(2));
                destination.setText(cursor.getString(3));
                departureDate.setText(cursor.getString(4));
                arrivalDate.setText(cursor.getString(5));
                departureTime.setText(cursor.getString(6));
                arrivalTime.setText(cursor.getString(7));
                flightDuration.setText(cursor.getString(8));
                fare.setText("$" + cursor.getString(9));
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {

        }
    }

    public void bookFlight(int clientID){
        try{

            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            DatabaseHelper.insertItinerary(db, flightID, clientID);


        }catch(SQLiteException e){

        }
    }

    public Dialog bookFlightDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder.setMessage("Your flight booked successfully. ")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {



                    }
                });

        return builder.create();
    }

}
