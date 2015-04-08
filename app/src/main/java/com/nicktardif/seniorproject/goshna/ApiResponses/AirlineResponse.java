package com.nicktardif.seniorproject.goshna.ApiResponses;

import com.nicktardif.seniorproject.goshna.Airline;

import java.util.List;

public class AirlineResponse {
    public List<Airline> airlines;

    public AirlineResponse(List<Airline> airlines) {
        this.airlines = airlines;
    }

    @Override
    public String toString() {
        return "AirlineResponse{" +
                "airlines=" + airlines +
                '}';
    }
}
