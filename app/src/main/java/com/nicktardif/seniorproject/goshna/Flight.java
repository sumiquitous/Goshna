package com.nicktardif.seniorproject.goshna;

import android.text.format.Time;

/**
 * Created by tick on 3/19/15.
 */
public class Flight {
    private String sourceAirport;
    private String destinationAirport;
    private String flightNumber;
    private Time departureTime;

    public Flight() {

    }

    public Flight(String sourceAirport, String destinationAirport, String flightNumber, Time departureTime) {
        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.flightNumber = flightNumber;
        this.departureTime = departureTime;
    }

    public String getSourceAirport() {
        return this.sourceAirport;
    }

    public String getDestinationAirport() {
        return this.destinationAirport;
    }

    public String getFlightNumber() {
        return this.flightNumber;
    }

    public Time getDepartureTime() {
        return this.departureTime;
    }
}
