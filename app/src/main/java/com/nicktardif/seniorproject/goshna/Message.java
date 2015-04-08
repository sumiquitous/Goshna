package com.nicktardif.seniorproject.goshna;

/**
 * Created by tick on 4/7/15.
 */
public class Message {
    public int flight_id;
    public String text;
    public int time;

    public Message(int flight_id, String text, int time) {
        this.flight_id = flight_id;
        this.text = text;
        this.time = time;
    }

    @Override
    public String toString() {
        return "MessageResponse{" +
                "flight_id=" + flight_id +
                ", text='" + text + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
