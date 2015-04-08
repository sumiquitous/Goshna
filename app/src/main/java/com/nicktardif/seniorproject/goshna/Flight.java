package com.nicktardif.seniorproject.goshna;

public class Flight {
    public int id;
    public String date;
    public String airline_short;
    public String source_short;
    public String dest_short;
    public int number;
    public int departure_time;
    public int airline_id;
    public int dest_id;
    public int source_id;

    public Flight(String date, String airline_short, String source_short, String dest_short, int number, int departure_time, int id, int airline_id, int dest_id, int source_id) {
        this.date = date;
        this.airline_short = airline_short;
        this.source_short = source_short;
        this.dest_short = dest_short;
        this.number = number;
        this.departure_time = departure_time;
        this.id = id;
        this.airline_id = airline_id;
        this.dest_id = dest_id;
        this.source_id = source_id;
    }

    @Override
    public String toString() {
        return "Flight{" +
                "date='" + date + '\'' +
                ", airline_short='" + airline_short + '\'' +
                ", source_short='" + source_short + '\'' +
                ", dest_short='" + dest_short + '\'' +
                ", number=" + number +
                ", departure_time=" + departure_time +
                ", id=" + id +
                ", airline_id=" + airline_id +
                ", dest_id=" + dest_id +
                ", source_id=" + source_id +
                '}';
    }
}
