package com.nicktardif.seniorproject.goshna;

import android.text.format.Time;
import java.util.Date;

public class Flight {
    private String airline;
    private String flightCode;
    private Date departureDate;

    private String sourceAirport;
    private String destinationAirport;
    private Time departureTime;

    public Flight() {

    }

    public Flight(String airline, String flightCode, Date departureDate, String sourceAirport, String destinationAirport, Time departureTime) {
        this.airline = airline;
        this.flightCode = flightCode;
        this.departureDate = departureDate;

        this.sourceAirport = sourceAirport;
        this.destinationAirport = destinationAirport;
        this.departureTime = departureTime;
    }

    public String getAirline() {
        return this.sourceAirport;
    }

    public String getFlightCode() {
        return this.flightCode;
    }

    public Date getDepartureDate() {
        return this.departureDate;
    }

    public String getSourceAirport() {
        return this.sourceAirport;
    }

    public String getDestinationAirport() {
        return this.destinationAirport;
    }

    public Time getDepartureTime() {
        return this.departureTime;
    }
}
