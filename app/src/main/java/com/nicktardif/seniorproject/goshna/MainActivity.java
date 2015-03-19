package com.nicktardif.seniorproject.goshna;

import android.content.Intent;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;


public class MainActivity extends ActionBarActivity {
    Button addFlightsButton;
    NotificationAdapter notificationAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addFlightsButton = (Button) findViewById(R.id.add_flight_link);

        // Set the onClick of the "Add Flights" button to open the Add Flights activity
        addFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), AddFlightActivity.class);
                startActivity(intent);
            }
        });

        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ticknardif", "Android ID is: " + androidId);

        notificationAdapter = new NotificationAdapter(this, R.layout.notification_list_item);
        ListView notificationListView = (ListView) findViewById(R.id.notification_list);
        notificationListView.setAdapter(notificationAdapter);

        notificationAdapter.add(new GoshnaNotification("Flight1", "Message1"));
        notificationAdapter.add(new GoshnaNotification("Flight2", "Message2"));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
