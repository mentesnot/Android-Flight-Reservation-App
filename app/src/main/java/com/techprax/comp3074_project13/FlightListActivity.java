 package com.techprax.comp3074_project13;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

 public class FlightListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flight_list);

        /*ListView flightList = (ListView) findViewById(R.id.flightList);

        Cursor cursor;

        CursorAdapter listAdapter = new SimpleCursorAdapter(getApplicationContext(),
                R.layout.custom_list_view,
                cursor,
                new String[]{"TIME", "FARE", "AIRLINE", "TRAVELTIME","CLASS"},
                new int[]{R.id.txtTime, R.id.txtFare, R.id.txtAirline, R.id.txtTravelTime, R.id.txtClass},//map the contents of NAME col to text in ListView
                0);

        flightList.setAdapter(listAdapter);*/
    }
}
