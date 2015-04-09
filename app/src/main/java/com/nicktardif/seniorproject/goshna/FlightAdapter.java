package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

        import java.util.List;

public class FlightAdapter extends ArrayAdapter<RegisteredFlight> {

    public FlightAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public FlightAdapter(Context context, int textViewResourceId, List<RegisteredFlight> notifications) {
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

        RegisteredFlight flight = getItem(position);

        if (flight != null) {
            TextView tripTV = (TextView) view.findViewById(R.id.flight_trip);
            TextView codeTV = (TextView) view.findViewById(R.id.flight_code);
            TextView departureTimeTV = (TextView) view.findViewById(R.id.flight_departure_time);
            LinearLayout wrapperLayout = (LinearLayout) view.findViewById(R.id.flight_wrapper);

            // Set the message content in the TextView
            if(tripTV != null) {
                String source = flight.flight.source_short;
                String destination = flight.flight.dest_short;
                String text = source + " -> " + destination;
                tripTV.setText(text);
            }
            if(codeTV != null) {
                String code = flight.flight.airline_short + Integer.toString(flight.flight.number);
                codeTV.setText("Flight Code: " + code);
            }
            if(departureTimeTV != null) {
                departureTimeTV.setText(MessageAdapter.timeToString(flight.flight.departure_time));
            }

            if(wrapperLayout != null) {
                if(flight.registered) {
                    wrapperLayout.setBackgroundColor(getContext().getResources().getColor(R.color.lightest_yellow));
                }
                else {
                    wrapperLayout.setBackgroundColor(Color.WHITE);
                }
            }
        }

        return view;
    }
}
