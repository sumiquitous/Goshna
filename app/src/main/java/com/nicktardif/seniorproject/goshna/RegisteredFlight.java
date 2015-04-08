package com.nicktardif.seniorproject.goshna;

/**
 * Created by tick on 4/8/15.
 */
public class RegisteredFlight {
    public Flight flight;
    public boolean registered;

    public RegisteredFlight(Flight flight) {
        this.flight = flight;
    }

    public RegisteredFlight(Flight flight, boolean registered) {
        this.flight = flight;
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "RegisteredFlight{" +
                "flight=" + flight +
                ", registered=" + registered +
                '}';
    }
}
