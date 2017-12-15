package com.techprax.comp3074_project13;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "TRAVELAPP";
    private static final int DB_VERSION = 1;

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        updateMyDatabase(db, 0, DB_VERSION);
    }

    //requires API level 16 and above
    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.setForeignKeyConstraintsEnabled(true);
        }
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + "AIRLINE");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHT");
        db.execSQL("DROP TABLE IF EXISTS " + "SEAT");
        db.execSQL("DROP TABLE IF EXISTS " + "FLIGHTCLASS");
        db.execSQL("DROP TABLE IF EXISTS " + "CLIENT");
        db.execSQL("DROP TABLE IF EXISTS " + "ACCOUNT");
        db.execSQL("DROP TABLE IF EXISTS " + "ITINERARY");

        updateMyDatabase(db, oldVersion, newVersion);

    }

    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < 1) {

            //create tables here
            db.execSQL(createAirline());
            db.execSQL(createFlight());
            db.execSQL(createSeat());
            db.execSQL(createFlightClass());
            db.execSQL(createClient());
            db.execSQL(createAccount());
            db.execSQL(createItinerary());

            //insert default data fro testing

            insertAirline(db, "Air Canada");
            insertAirline(db, "Air France");
            insertAirline(db, "Air Transat");
            insertAirline(db, "Alitalia");
            insertAirline(db, "Austrian");
            insertAirline(db, "Delta");
            insertAirline(db, "Emirates");
            insertAirline(db, "InterJet");
            insertAirline(db, "Lufthansa");
            insertAirline(db, "United");
            insertAirline(db, "WestJet");

            insertFlight(db, 100001, "Toronto", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 1);
            insertFlight(db, 100002, "Toronto", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 2);
            insertFlight(db, 100003, "Toronto", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 3);
            insertFlight(db, 100004, "Toronto", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 4);
            insertFlight(db, 100005, "Toronto", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 5);
            insertFlight(db, 100006, "Ottawa", "Toronto", "2017-12-21", "12:10", "12:10", 350.00, 2, 6);
            insertFlight(db, 100007, "Edmonton", "Winnipeg", "2017-12-20", "12:10", "12:10", 350.00, 2, 3);
            insertFlight(db, 100008, "Montreal", "Edmonton", "2017-12-20", "12:10", "12:10", 350.00, 2, 4);
            insertFlight(db, 100009, "NewYork", "Edmonton", "2017-12-20", "12:10", "12:10", 350.00, 2, 5);
            insertFlight(db, 100010, "Quebec City", "NewYork", "2017-12-20", "12:10", "12:10", 350.00, 2, 6);
            insertFlight(db, 100011, "British Colombia", "Nova Scotia", "2017-12-20", "12:10", "12:10", 350.00, 2, 7);
            insertFlight(db, 100012, "Los Angeles", "Ottawa", "2017-12-20", "12:10", "12:10", 350.00, 2, 8);
            insertFlight(db, 100013, "Winnipeg", "Toronto", "2017-12-20", "12:10", "12:10", 350.00, 2, 9);
            insertFlight(db, 100014, "Nova Scotia", "NewYork", "2017-12-20", "12:10", "12:10", 350.00, 2, 10);

            insertSeat(db, 100, "F", 1, 1);
            insertSeat(db, 200, "F", 2, 1);
            insertSeat(db, 200, "F", 3, 1);
            insertSeat(db, 200, "F", 4, 1);
            insertSeat(db, 200, "F", 5, 1);
            insertSeat(db, 200, "F", 6, 1);

            insertFlightClass(db, "Economy");
            insertFlightClass(db, "Business");

            insertClient(db, "John", "Doe", "76 Kendal Ave", "M5R 1L9", "Toronto", "ON", "Canada", "4164121000", "1232456598783241");

            insertAccount(db, "john@gmail.com", "password", 1);

        }

    }

    public String createAirline() {
        return "CREATE TABLE AIRLINE ("
                + "_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "AIRLINENAME TEXT);";
    }

    public String createFlight() {
        return "CREATE TABLE FLIGHT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHTNUMBER TEXT, " +
                "ORIGIN TEXT, " +
                "DESTINATION TEXT, " +
                "DEPARTUREDATE TEXT, " +
                "DEPARTURETIME TEXT, " +
                "ARRIVALTIME TEXT, " +
                "FLIGHTDURATION INTEGER, " +
                "FARE REAL, " +
                "FLIGHT_AIRLINE INTEGER, " +
                "FOREIGN KEY(FLIGHT_AIRLINE) REFERENCES AIRLINE(_id));";
    }

    public String createSeat() {
        return "CREATE TABLE SEAT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "SEATNUMBER TEXT, " +
                "SEAT_FLIGHT TEXT, " +
                "STATUS TEXT, " +
                "SEAT_FLIGHTCLASS TEXT, " +
                "FOREIGN KEY(SEAT_FLIGHT) REFERENCES FLIGHT(_id)," +
                "FOREIGN KEY(SEAT_FLIGHTCLASS) REFERENCES FLIGHTCLASS(_id));";
    }

    public String createFlightClass() {
        return "CREATE TABLE FLIGHTCLASS (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FLIGHTCLASSNAME TEXT);";
    }

    public String createClient() {
        return "CREATE TABLE CLIENT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "FIRSTNAME TEXT, " +
                "LASTNAME TEXT, " +
                "ADDRESS TEXT, " +
                "POSTALCODE TEXT, " +
                "CITY TEXT, " +
                "PROVINCE TEXT, " +
                "COUNTRY TEXT, " +
                "PHONE TEXT, " +
                "CREDITCARD TEXT, " +
                "IMAGE BLOB);";
    }

    public String createAccount() {
        return "CREATE TABLE ACCOUNT (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "EMAIL TEXT, " +
                "PASSWORD TEXT, " +
                "ACCOUNT_CLIENT INTEGER, " +
                "FOREIGN KEY (ACCOUNT_CLIENT) REFERENCES CLIENT(_id));";
    }

    public String createItinerary() {
        return "CREATE TABLE ITINERARY (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "TIMESTAMP DATETIME DEFAULT (STRFTIME('%d-%m-%Y   %H:%M', 'NOW','localtime')), " +
                "ITINERARY_CLIENT INTEGER, " +
                "ITINERARY_FLIGHT INTEGER, " +
                "FOREIGN KEY(ITINERARY_CLIENT) REFERENCES CLIENT(_id), " +
                "FOREIGN KEY(ITINERARY_FLIGHT) REFERENCES FLIGHT(_id));";
    }

    public void insertAirline(SQLiteDatabase db, String airlineName) {
        ContentValues airlineValues = new ContentValues();
        airlineValues.put("AIRLINENAME", airlineName);
        db.insert("AIRLINE", null, airlineValues);
    }

    public void insertFlight(SQLiteDatabase db, int flightNumber, String origin, String destination, String departureDate, String departureTime, String arrivalTime, Double fare, int flightDuration, int airlineID) {
        ContentValues flightValues = new ContentValues();
        flightValues.put("FLIGHTNUMBER", flightNumber);
        flightValues.put("ORIGIN", origin);
        flightValues.put("DESTINATION", destination);
        flightValues.put("DEPARTUREDATE", departureDate);
        flightValues.put("DEPARTURETIME", departureTime);
        flightValues.put("ARRIVALTIME", arrivalTime);
        flightValues.put("FARE", fare);
        flightValues.put("FLIGHTDURATION", flightDuration);
        flightValues.put("FLIGHT_AIRLINE", airlineID);
        db.insert("FLIGHT", null, flightValues);
    }

    public void insertSeat(SQLiteDatabase db, int seatNumber, String status, int flightID, int flightClassID) {
        ContentValues seatValues = new ContentValues();
        seatValues.put("SEATNUMBER", seatNumber);
        seatValues.put("STATUS", status);
        seatValues.put("SEAT_FLIGHT", flightID);
        seatValues.put("SEAT_FLIGHTCLASS", flightClassID);
        db.insert("SEAT", null, seatValues);
    }

    public void insertFlightClass(SQLiteDatabase db, String flightClassName) {
        ContentValues flightClassValues = new ContentValues();
        flightClassValues.put("FLIGHTCLASSNAME", flightClassName);
        db.insert("FLIGHTCLASS", null, flightClassValues);
    }

    public void insertClient(SQLiteDatabase db, String firstName, String lastName, String address, String postalCode,
                             String city, String province, String country, String phone, String creditCard) {
        ContentValues clientValues = new ContentValues();
        clientValues.put("FIRSTNAME", firstName);
        clientValues.put("LASTNAME", lastName);
        clientValues.put("ADDRESS", address);
        clientValues.put("POSTALCODE", postalCode);
        clientValues.put("CITY", city);
        clientValues.put("PROVINCE", province);
        clientValues.put("COUNTRY", country);
        clientValues.put("PHONE", phone);
        clientValues.put("CREDITCARD", creditCard);
        db.insert("CLIENT", null, clientValues);
    }

    public void insertAccount(SQLiteDatabase db, String email, String password, int clientID) {
        ContentValues accountValues = new ContentValues();
        accountValues.put("EMAIL", email);
        accountValues.put("PASSWORD", password);
        accountValues.put("ACCOUNT_CLIENT", clientID);
        db.insert("ACCOUNT", null, accountValues);
    }

    public static void insertItinerary(SQLiteDatabase db, int flightID, int clientID) {
        ContentValues itineraryValues = new ContentValues();
        itineraryValues.put("ITINERARY_FLIGHT", flightID);
        itineraryValues.put("ITINERARY_CLIENT", clientID);
        db.insert("ITINERARY", null, itineraryValues);
    }

    public static Cursor selectFlightWithID(SQLiteDatabase db, int flightID) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "WHERE FLIGHT._id = '" + flightID + "'", null);
    }

    public static Cursor searchFlight(SQLiteDatabase db, String origin, String destination,
                                      String departureDate, String flightClass) {
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id " +
                "WHERE ORIGIN = '" + origin +
                "' AND DESTINATION = '" + destination +
                "' AND DEPARTUREDATE = '" + departureDate +
                "' AND FLIGHTCLASSNAME = '" + flightClass +
                "' AND SEAT.STATUS = 'F'", null);
    }

    public static Cursor selectItinerary(SQLiteDatabase db){
        return db.rawQuery("SELECT FLIGHT._id, FLIGHTNUMBER, ORIGIN, DESTINATION, DEPARTUREDATE, DEPARTURETIME, " +
                " ARRIVALTIME, FLIGHTDURATION, FARE, AIRLINENAME, SEATNUMBER, FLIGHTCLASSNAME " +
                "FROM FLIGHT " +
                "INNER JOIN AIRLINE " +
                "ON FLIGHT.FLIGHT_AIRLINE = AIRLINE._id " +
                 "INNER JOIN ITINERARY " +
                "ON  FLIGHT._id = ITINERARY.ITINERARY_FLIGHT " +
                "INNER JOIN " +
                "SEAT " +
                "ON SEAT.SEAT_FLIGHT = FLIGHT._id " +
                "INNER JOIN " +
                "FLIGHTCLASS " +
                "ON SEAT.SEAT_FLIGHTCLASS = FLIGHTCLASS._id ", null);
    }

    public static void deleteItinerary(SQLiteDatabase db, int itineraryID){
        db.delete("ITINERARY", " _id = ? ", new String []{String.valueOf(itineraryID)});
    }

    public static Cursor selectItineraryWithFlightID(SQLiteDatabase db, int flightID){
       return db.query("ITINERARY", null, " ITINERARY.ITINERARY_FLIGHT = ? ",
               new String[]{String.valueOf(flightID)}, null, null, null, null);
    }


}
