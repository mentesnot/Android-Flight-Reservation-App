package com.techprax.comp3074_project13;

import java.util.Date;


public class Itinerary extends Flight{

    private int itineraryID;

    public Itinerary(String airline, int flightNumber, String origin, String destination,
                     Date departureDate, Date arrivalDate, Date departureTime, Date arrivalTime,
                     Double totalCost, int travelTime, String flightClass) {
        super(airline, flightNumber, origin, destination, departureDate, arrivalDate,
                departureTime, arrivalTime, totalCost, travelTime, flightClass);
    }


}
