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
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageButton;
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
    //custom dialog view
    private View dialogLayout;
    private TextView txtSortBy;
    private ListView sortList;
    private Button btnSort;
    private int sortByID = 100;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_way_flight_list);

        actionBar = getSupportActionBar();

        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
        currentTab = sharedPreferences.getInt("CURRENT_TAB", 0);
        origin = sharedPreferences.getString("ORIGIN", "");
        destination = sharedPreferences.getString("DESTINATION", "");
        departureDate = sharedPreferences.getString("DEPARTURE_DATE", "");
        returnDate = sharedPreferences.getString("RETURN_DATE", "");
        flightClass = sharedPreferences.getString("FLIGHT_CLASS", "");
        sortByID = sharedPreferences.getInt("ONEWAY_SORT_ID", 100);

        btnSort = (Button) findViewById(R.id.btnSort);


        btnSort.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sortDialog().show();
            }
        });

        oneWayFlightList = (ListView) findViewById(R.id.onewayFlightList);

        searchOneWayFlight(sortByID);

    }

    public void searchOneWayFlight(int sortByID) {

        try {
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();
            actionBar.setTitle("Oneway flight list");
            actionBar.setSubtitle(origin + " -> " + destination);

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
                flightNotFoundDialog().show();
            }

            oneWayFlightList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                    oneWayFightID = (int) id;
                    intent = new Intent(getApplicationContext(), CheckOutActivity.class);

                    sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.remove("ONEWAY_FLIGHT_ID");
                    editor.putInt("ONEWAY_FLIGHT_ID", oneWayFightID);

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
        builder.setMessage("The specified flight is not available. Please try again later.")
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

    //sort by dialog (one way)
    public Dialog sortDialog() {

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Sort by")
                .setItems(R.array.sort, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {

                        sharedPreferences = getSharedPreferences("PREFS", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("ONEWAY_SORT_ID");
                        editor.putInt("ONEWAY_SORT_ID", id);
                        editor.commit();

                        intent = new Intent(getApplicationContext(), OneWayFlightListActivity.class);
                        startActivity(intent);


                    }
                });

        return builder.create();
    }


}
