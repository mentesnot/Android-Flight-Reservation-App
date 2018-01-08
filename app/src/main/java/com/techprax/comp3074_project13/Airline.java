/*************************************************************************************************
 * JANUARY 8, 2018
 * COMP3074 - PROJECT 13
 * Members:
 *           HAMAD AHMAD:       101006399
 *           MENTESNOT ABOSET : 101022050
 *           TOAN NGUYEN:       100979753
 *           ZHENG LIU:         100970328
 * ************************************************************************************************/
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
