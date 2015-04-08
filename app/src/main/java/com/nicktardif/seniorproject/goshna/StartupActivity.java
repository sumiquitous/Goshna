package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.nicktardif.seniorproject.goshna.ApiResponses.IdResponse;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class StartupActivity extends ActionBarActivity {

    private SharedPreferences sharedPreferences;
    private GoshnaApiService api;

    private Callback<IdResponse> createUserCallback = new Callback<IdResponse>() {
        @Override
        public void success(IdResponse idResponse, Response response) {

            // Get the User ID from the response
            int user_id = idResponse.id;

            // Put the User ID in the shared preferences
            sharedPreferences.edit().putInt(getString(R.string.user_id_pref), user_id).apply();

            // If this is successful, start the Main Activity
            int delay = 500;
            startMainActivity(delay);
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("IdResponse was a failure, error: " + error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);

        // Check if the User ID is set, if not, get a user ID
        sharedPreferences = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        int user_id = sharedPreferences.getInt(getString(R.string.user_id_pref), -1);

        if(user_id == -1) {
            api.createUserId(createUserCallback);
        }
        else {
            int delay = 1500;
            startMainActivity(delay);
        }
    }

    private void startMainActivity(int delay) {
        new java.util.Timer().schedule(
                new java.util.TimerTask() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                },
            delay
            );
    }
}
