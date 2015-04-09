package com.nicktardif.seniorproject.goshna;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.nicktardif.seniorproject.goshna.ApiResponses.AirlineResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightID;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightIDResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.IdResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddFlightActivity extends ActionBarActivity {
    private Spinner airportSpinner;
    private Spinner airlineSpinner;
    private Button searchFlightsButton;
    private Button backToSearchButton;
    private Button goToMessagesButton;
    private DatePicker datePicker;
    private RelativeLayout searchSection;
    private RelativeLayout addFlightSection;
    private FlightAdapter flightAdapter;

    private ArrayAdapter<String> airportAdapter;
    private ArrayAdapter<String> airlineAdapter;

    private List<Airport> airportList;
    private List<Airline> airlineList;
    private List<RegisteredFlight> flightList;
    private List<Integer> userFlightIdList;

    private GoshnaApiService api;

    private int user_id;

    private String searchedAirport;
    private String searchedAirline;

    private Callback<AirportResponse> getAirportsCallback = new Callback<AirportResponse>() {
        @Override
        public void success(AirportResponse airportReponse, Response response) {

            Airport all = new Airport(0, "ALL", "All Airports");
            airportList.add(all);
            airportAdapter.add(all.airport_short);

            for(Airport airport :airportReponse.airports) {
                airportList.add(airport);
                airportAdapter.add(airport.airport_short);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("AirportResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<AirlineResponse> getAirlinesCallback = new Callback<AirlineResponse>() {
        @Override
        public void success(AirlineResponse airlineReponse, Response response) {

            Airline all = new Airline(0, "ALL", "All Airlines");
            airlineList.add(all);
            airlineAdapter.add(all.airline_short);

            for(Airline airline :airlineReponse.airlines) {
                airlineList.add(airline);
                airlineAdapter.add(airline.airline_short);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("AirlineResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<FlightResponse> findFlightsCallback = new Callback<FlightResponse>() {
        @Override
        public void success(FlightResponse flightResponse, Response response) {

            flightList.clear();
            flightAdapter.clear();

            for(Flight flight :flightResponse.flights) {
                boolean registered = false;

                if(userFlightIdList.contains(flight.id)) {
                    registered = true;
                }

                RegisteredFlight registeredFlight = new RegisteredFlight(flight, registered);
                flightAdapter.add(registeredFlight);
                flightList.add(registeredFlight);
            }

            TextView searchedTV = (TextView) findViewById(R.id.searched_info_text);
            String searchedInfoString = "Searching Airport: " + searchedAirport + " on Airline: " + searchedAirline;
            searchedTV.setText(searchedInfoString);

            // Toggle which section is seen
            searchSection.setVisibility(View.INVISIBLE);
            addFlightSection.setVisibility(View.VISIBLE);
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("FlightResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<FlightIDResponse> getUserFlightsCallback = new Callback<FlightIDResponse>() {
        @Override
        public void success(FlightIDResponse flightsResponse, Response response) {

            userFlightIdList.clear();

            // Add the Flight ID to the userFlightIdList
            for(FlightID flightID: flightsResponse.flights) {
                int flight_id = flightID.flight_id;
                userFlightIdList.add(flight_id);
            }
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("FlightResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<IdResponse> addUserToFlightCallback = new Callback<IdResponse>() {
        @Override
        public void success(IdResponse airportReponse, Response response) {
            String message = "Successfully added to the flight!";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("IdResponse was a failure, error: " + error.toString());
        }
    };

    private Callback<IdResponse> removeUserFromFlightCallback = new Callback<IdResponse>() {
        @Override
        public void success(IdResponse airportReponse, Response response) {
            String message = "Successfully removed from the flight!";
            Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("IdResponse was a failure, error: " + error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        searchFlightsButton = (Button) findViewById(R.id.search_flights_button);
        backToSearchButton = (Button) findViewById(R.id.back_to_search_button);
        goToMessagesButton = (Button) findViewById(R.id.go_home_button);
        datePicker = (DatePicker) findViewById(R.id.add_flight_date);
        searchSection = (RelativeLayout) findViewById(R.id.search_section);
        addFlightSection = (RelativeLayout) findViewById(R.id.add_flight_section);

        flightAdapter = new FlightAdapter(getBaseContext(), R.layout.flight_list_item);
        ListView flightListView = (ListView) findViewById(R.id.add_flight_list);
        flightListView.setAdapter(flightAdapter);

        flightListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView adapterView, View view, int pos, long id) {
                RegisteredFlight flight = flightList.get(pos);
                if(flight.registered) {
                    api.removeUserFromFlight(user_id, flight.flight.id, removeUserFromFlightCallback);
                    //flight.registered = false;
                    flightList.get(pos).registered = false;
                    flightAdapter.getItem(pos).registered = false;
                    adapterView.invalidate();
                } else {
                    api.addUserToFlight(user_id, flight.flight.id, addUserToFlightCallback);
                    //flight.registered = true;
                    flightList.get(pos).registered = true;
                    flightAdapter.getItem(pos).registered = true;
                }

                flightAdapter.notifyDataSetChanged();
            }

        });

        flightList = new ArrayList<RegisteredFlight>();
        userFlightIdList = new ArrayList<Integer>();

        // Set up our API service with Retrofit
        RestAdapter restAdapter = new RestAdapter.Builder()
                .setEndpoint("http://45.55.132.122:5000/goshna/api")
                .setLogLevel(RestAdapter.LogLevel.FULL)
                .build();
        api = restAdapter.create(GoshnaApiService.class);

        // Set the onClick listeners for the buttons
        setOnClickListeners();

        // Set up Airport Spinner
        airportAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        airportList = new ArrayList<Airport>();

        airportSpinner = (Spinner) findViewById(R.id.airport_spinner);
        airportAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airportSpinner.setAdapter(airportAdapter);

        // Set up Airline Spinner
        airlineAdapter = new ArrayAdapter(this, R.layout.support_simple_spinner_dropdown_item);
        airlineList = new ArrayList<Airline>();

        airlineSpinner = (Spinner) findViewById(R.id.airline_spinner);
        airlineAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        airlineSpinner.setAdapter(airlineAdapter);

        SharedPreferences sharedPreferences = this.getSharedPreferences(getString(R.string.prefs_file), Context.MODE_PRIVATE);
        user_id = sharedPreferences.getInt(getString(R.string.user_id_pref), -1);

        // Trigger the API to get all the FlightIDs that a user is registered for
        api.getUserFlightIds(user_id, getUserFlightsCallback);

        // Trigger the API to populate the Spinners
        api.getAllAirports(getAirportsCallback);
        api.getAllAirlines(getAirlinesCallback);
    }

    private void setOnClickListeners() {
        searchFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the airport and airline IDs
                int airport_id = 0;
                int airline_id = 0;

                if(airportList.get(airportSpinner.getSelectedItemPosition()) != null) {
                    searchedAirport = (String) airportSpinner.getSelectedItem();
                    airport_id = airportList.get(airportSpinner.getSelectedItemPosition()).id;
                }

                if(airlineList.get(airlineSpinner.getSelectedItemPosition()) != null) {
                    searchedAirline = (String) airlineSpinner.getSelectedItem();
                    airline_id = airlineList.get(airlineSpinner.getSelectedItemPosition()).id;
                }

                // TODO: Get the date correctly

                // Create the findFlights parameters
                FlightSearchCriteria criteria = new FlightSearchCriteria(airport_id, airline_id, "");

                // Make the API request
                api.findFlights(criteria, findFlightsCallback);
            }
        });

        backToSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle which section is seen
                searchSection.setVisibility(View.VISIBLE);
                addFlightSection.setVisibility(View.INVISIBLE);
            }
        });

        goToMessagesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private Date getDate(DatePicker datePicker) {
        int day = datePicker.getDayOfMonth();
        int month = datePicker.getMonth();
        int year = datePicker.getYear();

        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        return calendar.getTime();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_flight, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        return super.onOptionsItemSelected(item);
    }
}
