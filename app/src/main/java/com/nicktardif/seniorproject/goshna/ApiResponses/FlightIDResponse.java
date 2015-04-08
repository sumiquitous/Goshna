package com.nicktardif.seniorproject.goshna.ApiResponses;

import java.util.List;

/**
 * Created by tick on 4/8/15.
 */
public class FlightIDResponse {
    public List<FlightID> flights;

    public FlightIDResponse(List<FlightID> flights) {
        this.flights = flights;
    }
}
