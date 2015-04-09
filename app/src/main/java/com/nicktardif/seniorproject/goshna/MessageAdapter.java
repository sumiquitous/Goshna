package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class MessageAdapter extends ArrayAdapter<Message> {

    public MessageAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public MessageAdapter(Context context, int textViewResourceId, List<Message> messages) {
        super(context, textViewResourceId, messages);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.message_list_item, null);
        }

        Message message = getItem(position);

        if (message != null) {
            TextView timeTV = (TextView) view.findViewById(R.id.message_time);
            TextView textTV = (TextView) view.findViewById(R.id.message_text);
            TextView flightTV = (TextView) view.findViewById(R.id.message_flight);

            // Set the message content in the TextView
            if(timeTV != null) {
                timeTV.setText(timeToString(message.time));
            }

            if(flightTV != null) {
                flightTV.setText("Flight ID: " + Integer.toString(message.flight_id));
            }

            if(textTV != null) {
                textTV.setText(message.text);
            }
        }

        return view;
    }

    public static String timeToString(int time) {
        String end = " AM";

        if (time > 60 * 12) {
            end = " PM";
        }

        int hours = (time / 60) % 12;
        int minutes = time % 60;

        String minutesString = Integer.toString(minutes);
        if (minutesString.equals("0")) minutesString = "00";

        return Integer.toString(hours) + ":" + minutesString + end;
    }
}
