package com.techprax.comp3074_project13;



public class Airline {

    private int airlineID;
    private String airlineName;

    public Airline(String airlineName) {
        this.airlineName = airlineName;
    }

    public int getAirlineID() {
        return airlineID;
    }

    public void setAirlineID(int airlineID) {
        this.airlineID = airlineID;
    }

    public String getAirlineName() {
        return airlineName;
    }

    public void setAirlineName(String airlineName) {
        this.airlineName = airlineName;
    }
}
