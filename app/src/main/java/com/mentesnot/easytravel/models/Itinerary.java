/*************************************************************************************************
 * JANUARY 8, 2018
 * COMP3074 - PROJECT 13
 * Members:
 *           HAMAD AHMAD:       101006399
 *           MENTESNOT ABOSET : 101022050
 *           TOAN NGUYEN:       100979753
 *           ZHENG LIU:         100970328
 * ************************************************************************************************/
package com.mentesnot.easytravel.models;

import java.util.Date;


public class Itinerary {

    private int itineraryID;
    private Date timeStamp;

    public int getItineraryID() {
        return itineraryID;
    }

    public void setItineraryID(int itineraryID) {
        this.itineraryID = itineraryID;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
