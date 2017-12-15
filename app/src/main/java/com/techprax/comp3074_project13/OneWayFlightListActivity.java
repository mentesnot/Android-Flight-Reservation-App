package com.techprax.comp3074_project13;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ListActivity;
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

public class OneWayFlightListActivity extends AppCompatActivity {

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
    private ListView oneWayFlightList;
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
        setContentView(R.layout.activity_one_way_flight_list);

        //bundle = getIntent().getExtras();
        actionBar = getSupportActionBar();

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        currentTab = sharedPreferences.getInt("CURRENT_TAB", 0);
        origin = sharedPreferences.getString("ORIGIN", "");
        destination = sharedPreferences.getString("DESTINATION", "");
        departureDate = sharedPreferences.getString("DEPARTURE_DATE", "");
        returnDate = sharedPreferences.getString("RETURN_DATE", "");
        flightClass = sharedPreferences.getString("FLIGHT_CLASS", "");

        //Toast.makeText(getApplicationContext(), origin, Toast.LENGTH_SHORT).show();


       // txtMessage = (TextView) findViewById(R.id.txtMessage);
        oneWayFlightList = (ListView) findViewById(R.id.onewayFlightList);


        searchOneWayFlight();

    }

    public void searchOneWayFlight() {

        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            actionBar.setTitle("Oneway flight list");
            actionBar.setSubtitle(origin + " -> " + destination);

            cursor = DatabaseHelper.searchFlight(db, origin, destination,
                    departureDate, flightClass);

            if (cursor != null && cursor.getCount() > 0) {

                //Toast.makeText(getApplicationContext(), "Im working", Toast.LENGTH_SHORT).show();


                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_list_view,
                        cursor,
                        new String[]{"DEPARTURETIME", "ARRIVALTIME", "FARE", "AIRLINENAME",
                                "FLIGHTDURATION", "FLIGHTCLASSNAME"},
                        new int[]{R.id.txtDepartureTime, R.id.txtArrivalTime, R.id.txtFare,
                                R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass},
                        0);

                oneWayFlightList.setAdapter(listAdapter);

            } else {
                //txtMessage.setText("The specified flight is not available. Please try again later.");
                //Toast.makeText(getApplicationContext(), "Flight unavailable", Toast.LENGTH_SHORT).show();
                flightNotFoundDialog().show();
            }

            oneWayFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    oneWayFightID = (int) id;
                    Toast.makeText(getApplicationContext(), String.valueOf(oneWayFightID), Toast.LENGTH_SHORT).show();

                    intent = new Intent(getApplicationContext(), CheckOutActivity.class);

                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("ONEWAY_FLIGHT_ID");
                    editor.putInt("ONEWAY_FLIGHT_ID", oneWayFightID);

                    editor.commit();

                    //intent.putExtra("SELECTED_FLIGHT_ID", oneWayFightID);

                    startActivity(intent);
                }
            });

        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
        }
    }

    public Dialog flightNotFoundDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The specified flight is not available. Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }

}
