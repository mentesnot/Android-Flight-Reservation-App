package com.techprax.comp3074_project13;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class CheckOutActivity extends AppCompatActivity {

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_out);

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        flightID = sharedPreferences.getInt("ONEWAY_FLIGHT_ID", 0);

        airline = (TextView) findViewById(R.id.txtCheckoutAirline);
        flightNo = (TextView) findViewById(R.id.txtCheckoutFlightNumber);
        origin = (TextView) findViewById(R.id.txtCheckoutOrigin);
        destination = (TextView) findViewById(R.id.txtCheckoutDestination);
        departureDate = (TextView) findViewById(R.id.txtCheckoutDepartureDate);
        arrivalDate = (TextView) findViewById(R.id.txtCheckoutArrivalDate);
        departureTime = (TextView) findViewById(R.id.txtCheckoutDepartureTime);
        arrivalTime = (TextView) findViewById(R.id.txtCheckoutArrivalTime);
        flightDuration = (TextView) findViewById(R.id.txtCheckoutFlightDuration);
        flightClass = (TextView) findViewById(R.id.txtCheckoutFlightClass);
        fare = (TextView) findViewById(R.id.txtCheckoutFare);
        totalFare = (TextView) findViewById(R.id.txtCheckoutTotalFare);

        displaySelectedFlightInfo(flightID);

        Toast.makeText(getApplicationContext(), String.valueOf(flightID), Toast.LENGTH_SHORT).show();
    }

    public void displaySelectedFlightInfo(int id) {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            cursor = DatabaseHelper.selectFlightWithID(db, id);

            if (cursor != null && cursor.getCount() > 0) {
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
}
