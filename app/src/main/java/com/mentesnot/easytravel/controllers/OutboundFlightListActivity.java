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
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.mentesnot.easytravel.database.DatabaseHelper;
import com.mentesnot.easytravel.HelperUtils.HelperUtilities;
import com.mentesnot.easytravel.R;

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
    private int sortByID;
    private Button btnSort;
    private TextView flightNotFound;
    private boolean flightUnavailable = false;


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
        sortByID = sharedPreferences.getInt("OUTBOUND_SORT_ID", 100);

        flightList = (ListView) findViewById(R.id.outboundFlightList);

        btnSort = (Button) findViewById(R.id.btnOutboundSort);

        flightNotFound = (TextView)findViewById(R.id.txtOutboundFlightNotFound);

        flightNotFound.setVisibility(View.INVISIBLE);



        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialog().show();
            }
        });


        searchOutboundFlight();

    }
    public void searchOutboundFlight() {
        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            //outbound flight
            if (sortByID == 0) {
                cursor = DatabaseHelper.selectFlight(db, origin, destination,
                        departureDate, flightClass, "FARE");
            } else if (sortByID == 1) {
                cursor = DatabaseHelper.selectFlight(db, origin, destination,
                        departureDate, flightClass, "FLIGHTDURATION");
            } else {
                cursor = DatabaseHelper.selectFlight(db, origin, destination,
                        departureDate, flightClass);
            }

            if (cursor != null && cursor.getCount() > 0) {

                actionBar.setTitle("Select outbound flight");
                actionBar.setSubtitle(HelperUtilities.capitalize(origin) + " -> " + HelperUtilities.capitalize(destination));

                CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                        R.layout.custom_list_view,
                        cursor,
                        new String[]{"DEPARTURETIME", "ARRIVALTIME", "FARE", "AIRLINENAME", "FLIGHTDURATION", "FLIGHTCLASSNAME"},
                        new int[]{R.id.txtDepartureTime, R.id.txtArrivalTime, R.id.txtFare, R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass},//map the contents of NAME col to text in ListView
                        0);

                flightList.setAdapter(listAdapter);

            }else{
                flightUnavailable = true;
            }

            cursor = DatabaseHelper.selectFlight(db, destination, origin,
                    returnDate, flightClass);

            if (cursor != null && cursor.getCount() > 0){
                //do nothing here

            }else{
                flightUnavailable = true;
            }

            //Toast.makeText(getApplicationContext(), String.valueOf(flightUnavailable), Toast.LENGTH_SHORT).show();

            if(flightUnavailable == true){
                flightNotFound.setVisibility(View.VISIBLE);
                btnSort.setVisibility(View.INVISIBLE);
            }

            flightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    outboundFlightID = (int) id;

                    intent = new Intent(getApplicationContext(), ReturnFlightListActivity.class);
                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("OUTBOUND_FLIGHT_ID");
                    editor.putInt("OUTBOUND_FLIGHT_ID", outboundFlightID);

                    editor.commit();

                    startActivity(intent);
                    finish();
                }
            });


        } catch (SQLiteException e) {
            Toast.makeText(getApplicationContext(), "Database error", Toast.LENGTH_SHORT).show();
        }

    }

    public Dialog flightNotFoundDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("The specified round flight is not available. Please try again later.")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });

        return builder.create();
    }

    //sort by dialog (outbound)
    public Dialog sortDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setItems(R.array.sort, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("OUTBOUND_SORT_ID");
                        editor.putInt("OUTBOUND_SORT_ID", id);
                        editor.commit();

                        intent = new Intent(getApplicationContext(), OutboundFlightListActivity.class);
                        startActivity(intent);
                    }
                });

        return builder.create();
    }


}
