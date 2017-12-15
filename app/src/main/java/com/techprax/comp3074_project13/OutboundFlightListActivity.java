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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class OutboundFlightListActivity extends AppCompatActivity {

    private SharedPreferences sharedPreferences;
    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;
    private String origin;
    private String destination;
    private String departureDate;
    private String returnDate;
    private String flightClass;
    private TextView txtMessage;
    private ListView flightList;
    private Bundle bundle;
    private int currentTab;
    private int oneWayFightID;
    private int outboundFlightID;
    private int returnFlightID;
    private Intent intent;
    private android.support.v7.app.ActionBar actionBar;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_outbound_flight_list);

        //bundle = getIntent().getExtras();
        actionBar = getSupportActionBar();

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        currentTab = sharedPreferences.getInt("CURRENT_TAB", 0);
        origin = sharedPreferences.getString("ORIGIN", "");
        destination = sharedPreferences.getString("DESTINATION", "");
        departureDate = sharedPreferences.getString("DEPARTURE_DATE", "");
        returnDate = sharedPreferences.getString("RETURN_DATE", "");
        flightClass = sharedPreferences.getString("FLIGHT_CLASS", "");


        flightList = (ListView) findViewById(R.id.outboundFlightList);


        searchOutboundFlight();

    }
    public void searchOutboundFlight() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            //departure flight
            cursor = DatabaseHelper.searchFlight(db, origin, destination,
                    departureDate, flightClass);

            if (cursor != null && cursor.getCount() > 0) {

                actionBar.setTitle("Select outbound flight");
                actionBar.setSubtitle(origin + " -> " + destination);

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_list_view,
                        cursor,
                        new String[]{"DEPARTURETIME", "ARRIVALTIME", "FARE", "AIRLINENAME", "FLIGHTDURATION", "FLIGHTCLASSNAME"},
                        new int[]{R.id.txtDepartureTime, R.id.txtArrivalTime, R.id.txtFare, R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass},//map the contents of NAME col to text in ListView
                        0);

                flightList.setAdapter(listAdapter);

                //Toast.makeText(getApplicationContext(), String.valueOf(cursor.getInt(0)), Toast.LENGTH_SHORT).show();

            } else {
                //Toast.makeText(getApplicationContext(), "Flight not found", Toast.LENGTH_SHORT).show();
                flightNotFoundDialog().show();
            }

            flightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    outboundFlightID = (int) id;
                    //Toast.makeText(getApplicationContext(), String.valueOf(oneWayFightID), Toast.LENGTH_SHORT).show();

                    intent = new Intent(getApplicationContext(), ReturnFlightListActivity.class);
                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("OUTBOUND_FLIGHT_ID");
                    editor.putInt("OUTBOUND_FLIGHT_ID", outboundFlightID);

                    editor.commit();

                    startActivity(intent);
                }
            });


        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
        }

    }

    public Dialog flightNotFoundDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The specified outbound flight is not available. Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), ReturnFlightListActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }


}
