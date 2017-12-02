package com.techprax.comp3074_project13;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TabHost;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        DatePickerDialog.OnDateSetListener {
    private int travellerCount = 1;
    private TextView numTraveller;
    private ImageButton imgBtnAdd;
    private ImageButton imgBtnRemove;
    private View dialoglayout;

    private TextView txtOneWayFrom;
    private TextView txtOneWayTo;
    private Button btnOneWayDepartureDatePicker;
    private Button btnOneWayClass;
    private Button btnOneWayNumTraveller;

    private TextView txtRoundFrom;
    private TextView txtRoundTo;
    private Button btnRoundDepartureDatePicker;
    private Button btnRoundReturnDatePicker;
    private Button btnRoundClass;
    private Button btnRoundNumTraveller;

    private Button btnSearch;

    private int tempID = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //navigation drawer manager
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //tab host manager
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);
        tabHost.setup();

        //Tab 1
        TabHost.TabSpec spec = tabHost.newTabSpec("Tab One");
        spec.setContent(R.id.tab1);
        spec.setIndicator("One way");
        tabHost.addTab(spec);

        //Tab 2
        spec = tabHost.newTabSpec("Tab Two");
        spec.setContent(R.id.tab2);
        spec.setIndicator("Round Trip");
        tabHost.addTab(spec);

        //tab text color
        for (int i = 0; i < tabHost.getTabWidget().getChildCount(); i++) {
            TextView tv = (TextView) tabHost.getTabWidget().getChildAt(i).findViewById(android.R.id.title);
            tv.setTextColor(getResources().getColor(R.color.colorInverted));
        }

        //one way form
        txtOneWayFrom = (TextView) findViewById(R.id.txtOneWayFrom);
        txtOneWayTo = (TextView) findViewById(R.id.txtOneWayTo);
        btnOneWayDepartureDatePicker = (Button) findViewById(R.id.btnOneWayDepartureDatePicker);
        btnOneWayClass = (Button) findViewById(R.id.btnOneWayClass);
        btnOneWayNumTraveller = (Button) findViewById(R.id.btnOneWayNumTraveller);


        //round trip form
        txtRoundFrom = (TextView) findViewById(R.id.txtRoundFrom);
        txtRoundTo = (TextView) findViewById(R.id.txtRoundTo);
        btnRoundDepartureDatePicker = (Button) findViewById(R.id.btnRoundDepartureDatePicker);
        btnRoundReturnDatePicker = (Button) findViewById(R.id.btnRoundReturnDatePicker);
        btnRoundClass = (Button) findViewById(R.id.btnRoundClass);
        btnRoundNumTraveller = (Button) findViewById(R.id.btnRoundTraveller);

        btnSearch = (Button)findViewById(R.id.btnSearch);


        //one way departure date picker on click listener
        btnOneWayDepartureDatePicker.setOnClickListener(new View.OnClickListener() {

            //date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this, MainActivity.this,
                    HelperUtilities.currentYear(),
                    HelperUtilities.currentMonth(),
                    HelperUtilities.currentDay());

            @Override
            public void onClick(View view) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }
        });

        //round trip departure date picker on click listener
        btnRoundDepartureDatePicker.setOnClickListener(new View.OnClickListener() {

            //date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this, MainActivity.this,
                    HelperUtilities.currentYear(),
                    HelperUtilities.currentMonth(),
                    HelperUtilities.currentDay());

            @Override
            public void onClick(View view) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        //round trip return date picker on click listener
        btnRoundReturnDatePicker.setOnClickListener(new View.OnClickListener() {

            //date picker dialog
            DatePickerDialog datePickerDialog = new DatePickerDialog(
                    MainActivity.this, MainActivity.this,
                    HelperUtilities.currentYear(),
                    HelperUtilities.currentMonth(),
                    HelperUtilities.currentDay());

            @Override
            public void onClick(View view) {
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();
            }
        });

        //one way class selector on click listener
        btnOneWayClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classPickerDialog().show();
            }
        });

        //one way number of travellers on click listener
        btnOneWayNumTraveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travellerNumberDialog().show();
            }
        });

        //round trip class selector on click listener
        btnRoundClass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                classPickerDialog().show();
            }
        });

        // round trip number of traveller on click listener
        btnRoundNumTraveller.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                travellerNumberDialog().show();
            }
        });

        //searches flights on click
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //call search method here
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handles navigation view item on clicks
        int id = item.getItemId();

        if (id == R.id.nav_itinerary) {
            Intent intent = new Intent(getApplicationContext(), ItineraryActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_booked_flights) {
            Intent intent = new Intent(getApplicationContext(), BookedFlightActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_profile) {
            Intent intent = new Intent(getApplicationContext(), ProfileActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_security) {
            Intent intent = new Intent(getApplicationContext(), SecurityActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_logout) {

        }


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    //listens datepicker dialog selected date
    @Override
    public void onDateSet(DatePicker datePicker, int startYear, int starthMonth, int startDay) {

        btnOneWayDepartureDatePicker.setText(startYear + "/" + (starthMonth + 1) + "/" + startDay);

    }

    //class picker dialog
    public Dialog classPickerDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final CharSequence[] classList = {"Economy", "Business"}; //temp data, should be retrieved from database


        builder.setTitle("Select Class")
                .setSingleChoiceItems(classList, tempID, new DialogInterface
                        .OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int id) {
                        tempID = id;
                        //get selected class here
                        btnOneWayClass.setText(classList[id].toString());


                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {


                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        btnOneWayClass.setText(classList[tempID].toString());


        return builder.create();
    }

    //number of travellers dialog
    public Dialog travellerNumberDialog() {


        dialoglayout = getLayoutInflater().inflate(R.layout.custom_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);


        builder.setTitle("Number of travellers")
                .setView(dialoglayout)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //get number of traveller here
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });

        imgBtnRemove = (ImageButton) dialoglayout.findViewById(R.id.imgBtnRemove);
        imgBtnAdd = (ImageButton) dialoglayout.findViewById(R.id.imgBtnAdd);
        numTraveller = (TextView) dialoglayout.findViewById(R.id.txtNumber);

        imgBtnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                travellerCount++;
                numTraveller.setText(String.valueOf(travellerCount));
                btnOneWayNumTraveller.setText(String.valueOf(travellerCount) + " Traveller");
            }
        });

        imgBtnRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (travellerCount > 1) {
                    travellerCount--;
                }
                numTraveller.setText(String.valueOf(travellerCount));
                btnOneWayNumTraveller.setText(String.valueOf(travellerCount) + " Traveller");
            }
        });

        numTraveller.setText(String.valueOf(travellerCount));

        return builder.create();
    }

    /*public DatePickerDialog datePickerDialog(){

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                MainActivity.this, MainActivity.this,
                HelperUtilities.currentYear(),
                HelperUtilities.currentMonth(),
                HelperUtilities.currentDay());

        return datePickerDialog;

    }
*/


}
