package com.nicktardif.seniorproject.goshna.ApiResponses;

import com.nicktardif.seniorproject.goshna.Airport;

import java.util.List;

public class AirportResponse {
    public List<Airport> airports;

    public AirportResponse(List<Airport> airports) {
        this.airports = airports;
    }

    @Override
    public String toString() {
        return "AirportResponse{" +
                "airports=" + airports +
                '}';
    }
}
