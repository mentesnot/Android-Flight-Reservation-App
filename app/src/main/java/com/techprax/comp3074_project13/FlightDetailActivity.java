package com.techprax.comp3074_project13;

import android.app.AlertDialog;
import android.app.Dialog;
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
import android.widget.TextView;
import android.widget.Toast;

public class FlightDetailActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private int flightID;

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
    private Button btnCancelFlight;
    private Intent intent;
    private int itineraryID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_detail);

        intent = getIntent();
        flightID = intent.getIntExtra("FLIGHT_ID",0);

        Toast.makeText(getApplicationContext(), String.valueOf(flightID), Toast.LENGTH_SHORT).show();


        airline = (TextView) findViewById(R.id.txtAirlineDetail);
        flightNo = (TextView) findViewById(R.id.txtFlightNumberDetail);
        origin = (TextView) findViewById(R.id.txtOriginDetail);
        destination = (TextView) findViewById(R.id.txtDestinationDetail);
        departureDate = (TextView) findViewById(R.id.txtDepartureDateDetail);
        arrivalDate = (TextView) findViewById(R.id.txtArrivalDateDetail);
        departureTime = (TextView) findViewById(R.id.txtArrivalTimeDetail);
        arrivalTime = (TextView) findViewById(R.id.txtArrivalTimeDetail);
        flightDuration = (TextView) findViewById(R.id.txtFlightDurationDetail);
        flightClass = (TextView) findViewById(R.id.txtFlightClassDetail);
        fare = (TextView) findViewById(R.id.txtFareDetail);
        totalFare = (TextView) findViewById(R.id.txtTotalFareDetail);


        displaySelectedFlightInfo(flightID);

        btnCancelFlight = (Button) findViewById(R.id.btnCancelFlight) ;

        btnCancelFlight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cancelFlightAlert().show();

            }
        });
    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlight(db, id);

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
                totalFare.setText("$" + cursor.getString(9));
                airline.setText(cursor.getString(10));
                flightClass.setText(cursor.getString(12));
            }

        } catch (SQLiteException ex) {

        }
    }

    public void cancelFlight(){
        try{

            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getWritableDatabase();

            cursor = DatabaseHelper.selectItineraryWithFlightID(db, flightID);

            if (cursor != null && cursor.getCount() > 0) {
                cursor.moveToFirst();
                itineraryID = cursor.getInt(0);

               // Toast.makeText(getApplicationContext(), String.valueOf(itineraryID), Toast.LENGTH_SHORT).show();
            }

            DatabaseHelper.deleteItinerary(db, itineraryID);

        }catch(SQLiteException e){

        }
    }

    public Dialog cancelFlightAlert() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Are you sure you want to cancel your flight? ")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        cancelFlight();
                        Intent intent = new Intent(getApplicationContext(), ItineraryActivity.class);
                        startActivity(intent);
                    }
                })
            .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {



                }
            });

        return builder.create();
    }


}
