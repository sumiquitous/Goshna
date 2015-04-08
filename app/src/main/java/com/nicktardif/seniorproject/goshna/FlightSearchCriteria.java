package com.nicktardif.seniorproject.goshna;

/**
 * Created by tick on 4/7/15.
 */
public class FlightSearchCriteria {
    int airport_id;
    int airline_id;
    String date;

    public FlightSearchCriteria(int airport_id, int airline_id, String date) {
        this.airport_id = airport_id;
        this.airline_id = airline_id;
        this.date = date;
    }

    @Override
    public String toString() {
        return "FlightSearchCriteria{" +
                "airport_id=" + airport_id +
                ", airline_id=" + airline_id +
                ", date='" + date + '\'' +
                '}';
    }
}
