package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.nicktardif.seniorproject.goshna.ApiResponses.MessageResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity {
    MessageAdapter messageAdapter;

    int user_id;

    private GoshnaApiService api;

    private Callback<MessageResponse> getUserMessagesCallback = new Callback<MessageResponse>() {
        @Override
        public void success(MessageResponse messageResponse, Response response) {
            System.out.println(messageResponse.toString());

            messageAdapter.clear();

            // If there are no messages, display to the user that they can register for messages
            if(messageResponse.messages.size() == 0) {
                ((TextView) findViewById(R.id.no_messages_text)).setVisibility(View.VISIBLE);
            }

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

        setOnClickListeners();

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt(getString(R.string.user_id_pref), -1);

        messageAdapter = new MessageAdapter(this, R.layout.message_list_item);
        ListView messageListView = (ListView) findViewById(R.id.message_list);
        messageListView.setAdapter(messageAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();

        refreshMessages();
    }

    private void setOnClickListeners() {
    }

    private void refreshMessages() {
        api.getUserMessages(user_id, getUserMessagesCallback);
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
        if (id == R.id.action_refresh) {
            // Refresh the Messages in the List
            refreshMessages();

            return true;
        }

        if (id == R.id.action_flights) {
            // Go to the AddFlights Activity
            Intent intent = new Intent(getBaseContext(), AddFlightActivity.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
