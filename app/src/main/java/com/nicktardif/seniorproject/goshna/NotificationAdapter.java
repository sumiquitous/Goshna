package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class NotificationAdapter extends ArrayAdapter<GoshnaNotification> {

    public NotificationAdapter(Context context, int textViewResourceId) {
        super(context, textViewResourceId);
    }

    public NotificationAdapter(Context context, int textViewResourceId, List<GoshnaNotification> notifications) {
        super(context, textViewResourceId, notifications);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;

        if(view == null) {
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            view = vi.inflate(R.layout.notification_list_item, null);
        }

        GoshnaNotification notification = getItem(position);

        if (notification != null) {
            TextView flightTV = (TextView) view.findViewById(R.id.notification_flight);
            TextView messageTV = (TextView) view.findViewById(R.id.notification_message);

            // Set the message content in the TextView
            if(flightTV != null) {
                flightTV.setText(notification.getFlight());
            }

            if(messageTV != null) {
                messageTV.setText(notification.getMessage());
            }
        }

        return view;
    }
}
