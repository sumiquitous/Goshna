package com.nicktardif.seniorproject.goshna;

import android.text.format.Time;

public class GoshnaNotification {
    public String flight;
    public String message;
    public Time time;

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
