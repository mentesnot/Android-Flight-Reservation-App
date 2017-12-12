package com.techprax.comp3074_project13;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.CursorAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.Toast;

public class ItineraryActivity extends AppCompatActivity {

    private SQLiteOpenHelper databaseHelper;
    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itinerary);

        //ListView flightList = (ListView) findViewById(R.id.flightList);

        try{
            databaseHelper = new DatabaseHelper(getApplicationContext());
            db = databaseHelper.getReadableDatabase();

            /*cursor = DatabaseHelper.selectFlight(db);


            CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                    R.layout.custom_list_view,
                    cursor,
                    new String[]{"AIRLINENAME", "FARE", "DEPARTURETIME", "DESTINATION","FLIGHTCLASSNAME"},
                    new int[]{R.id.txtTime, R.id.txtFare, R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass},//map the contents of NAME col to text in ListView
                    0);
            flightList.setAdapter(listAdapter);*/


        }catch (SQLiteException e){
            Toast.makeText(getApplicationContext(), "Database unavailable", Toast.LENGTH_SHORT).show();
        }

    }
}
