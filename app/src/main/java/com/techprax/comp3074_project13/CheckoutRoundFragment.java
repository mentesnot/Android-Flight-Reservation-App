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
    private TextView fareReturn;
    private TextView totalFareReturn;

    private Button bookFlight;
    private int clientID;


    public CheckoutRoundFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_checkout_round, container, false);

        sharedPreferences = getActivity().getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        outboundFlightID = sharedPreferences.getInt("OUTBOUND_FLIGHT_ID", 0);
        returnFlightID = sharedPreferences.getInt("RETURN_FLIGHT_ID", 0);

        clientID = 1;

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
        fare = (TextView) view.findViewById(R.id.txtCheckoutFareOutbound);
        totalFare = (TextView) view.findViewById(R.id.txtCheckoutTotalFareOutbound);

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


        bookFlight = (Button)view.findViewById(R.id.btnRoundCheckout) ;
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

        displaySelectedOutboundFlightInfo(outboundFlightID);
        displaySelectedReturnFlightInfo(returnFlightID);
    }

    public void displaySelectedOutboundFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlightWithID(db, id);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                flightNo.setText(cursor.getString(1));
                origin.setText(cursor.getString(2));
                destination.setText(cursor.getString(3));
                departureDate.setText(cursor.getString(4));
                departureTime.setText(cursor.getString(5));
                arrivalTime.setText(cursor.getString(6));
                flightDuration.setText(cursor.getString(7));
                fare.setText(cursor.getString(8));
                airline.setText(cursor.getString(9));
                fare.setText("$" + cursor.getString(10));
                totalFare.setText("$" + cursor.getString(10));
                flightClass.setText(cursor.getString(11));
            }

        } catch (SQLiteException ex) {

        }
    }

    public void displaySelectedReturnFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlightWithID(db, id);

            if (cursor != null && cursor.getCount() == 1) {
                cursor.moveToFirst();
                flightNoReturn.setText(cursor.getString(1));
                originReturn.setText(cursor.getString(2));
                destinationReturn.setText(cursor.getString(3));
                departureDateReturn.setText(cursor.getString(4));
                departureTimeReturn.setText(cursor.getString(5));
                arrivalTimeReturn.setText(cursor.getString(6));
                flightDurationReturn.setText(cursor.getString(7));
                airlineReturn.setText(cursor.getString(9));
                flightClassReturn.setText(cursor.getString(11));
            }

        } catch (SQLiteException ex) {

        }
    }

    public void bookFlight(int clientID){
        try{

            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            DatabaseHelper.insertItinerary(db, outboundFlightID, clientID);
            DatabaseHelper.insertItinerary(db, returnFlightID, clientID);


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
