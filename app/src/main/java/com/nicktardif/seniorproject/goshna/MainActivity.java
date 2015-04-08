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

import com.nicktardif.seniorproject.goshna.ApiResponses.MessageResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    Button addFlightsButton;
    MessageAdapter messageAdapter;

    private GoshnaApiService api;

    private Callback<MessageResponse> getMessagesCallback = new Callback<MessageResponse>() {
        @Override
        public void success(MessageResponse messageResponse, Response response) {
            System.out.println(messageResponse.toString());

            for(Message message: messageResponse.messages) {
                messageAdapter.add(message);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("MessageResponse was a failure, error: " + error.toString());
        }
    };

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

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);


        String androidId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ticknardif", "Android ID is: " + androidId);

        messageAdapter = new MessageAdapter(this, R.layout.message_list_item);
        ListView messageListView = (ListView) findViewById(R.id.message_list);
        messageListView.setAdapter(messageAdapter);

        api.getMessages(getMessagesCallback);
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
