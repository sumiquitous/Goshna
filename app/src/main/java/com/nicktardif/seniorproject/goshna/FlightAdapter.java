package com.nicktardif.seniorproject.goshna;

import android.content.Context;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
        import android.widget.TextView;

        import java.util.List;

public class FlightAdapter extends ArrayAdapter<Flight> {

    public FlightAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FlightAdapter(Context context, int textViewResourceId, List<Flight> notifications) {
        super(context, textViewResourceId, notifications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.flight_list_item, null);
        }

        Flight flight = getItem(position);

        if (flight != null) {
            TextView sourceTV = (TextView) view.findViewById(R.id.flight_source);
            TextView destinationTV = (TextView) view.findViewById(R.id.flight_destination);
            TextView numberTV = (TextView) view.findViewById(R.id.flight_number);
            TextView departureTimeTV = (TextView) view.findViewById(R.id.flight_departure_time);

            // Set the message content in the TextView
            if(sourceTV != null) {
                sourceTV.setText(flight.getSourceAirport());
            }
            if(destinationTV != null) {
                destinationTV.setText(flight.getDestinationAirport());
            }
            if(numberTV != null) {
                numberTV.setText(flight.getFlightNumber());
            }
            if(departureTimeTV != null) {
                departureTimeTV.setText(flight.getDepartureTime().toString());
            }
        }

        return view;
    }
}
