package com.nicktardif.seniorproject.goshna;

public class GoshnaNotification {
    public String flight;
    public String message;

    public GoshnaNotification(String flight, String message) {
        this.flight = flight;
        this.message = message;
    }

    public String getFlight() {
        return flight;
    }

    public String getMessage() {
        return message;
    }
}
