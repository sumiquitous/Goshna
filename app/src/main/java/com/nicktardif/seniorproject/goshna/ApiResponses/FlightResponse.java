package com.nicktardif.seniorproject.goshna.ApiResponses;

import com.nicktardif.seniorproject.goshna.Flight;

import java.util.List;

/**
 * Created by tick on 4/7/15.
 */
public class FlightResponse {
    public List<Flight> flights;

    public FlightResponse(List<Flight> flights) {
        this.flights = flights;
    }

    @Override
    public String toString() {
        return "FlightResponse{" +
                "flights=" + flights +
                '}';
    }
}
