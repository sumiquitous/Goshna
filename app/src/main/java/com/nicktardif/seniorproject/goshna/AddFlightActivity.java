package com.nicktardif.seniorproject.goshna;

import android.app.DatePickerDialog;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.nicktardif.seniorproject.goshna.ApiResponses.AirlineResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightResponse;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class AddFlightActivity extends ActionBarActivity implements TextWatcher {
    private Spinner airportSpinner;
    private Spinner airlineSpinner;
    private Button searchFlightsButton;
    private Button backToSearchButton;
    private DatePicker datePicker;
    private RelativeLayout searchSection;
    private RelativeLayout addFlightSection;
    private FlightAdapter flightAdapter;

    private ArrayAdapter<String> airportAdapter;
    private ArrayAdapter<String> airlineAdapter;

    private List<Airport> airportList;
    private List<Airline> airlineList;
    private List<Flight> flightList;

    private GoshnaApiService api;

    private Callback<AirportResponse> getAirportsCallback = new Callback<AirportResponse>() {
        @Override
        public void success(AirportResponse airportReponse, Response response) {

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

            for(Flight flight :flightResponse.flights) {
                flightList.add(flight);
                flightAdapter.add(flight);
            }

            // Toggle which section is seen
            searchSection.setVisibility(View.INVISIBLE);
            addFlightSection.setVisibility(View.VISIBLE);
        }

        @Override
        public void failure(RetrofitError error) {
            System.err.println("FlightResponse was a failure, error: " + error.toString());
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        searchFlightsButton = (Button) findViewById(R.id.search_flights_button);
        backToSearchButton = (Button) findViewById(R.id.back_to_search_button);
        datePicker = (DatePicker) findViewById(R.id.add_flight_date);
        searchSection = (RelativeLayout) findViewById(R.id.search_section);
        addFlightSection = (RelativeLayout) findViewById(R.id.add_flight_section);

        flightAdapter = new FlightAdapter(getBaseContext(), R.layout.flight_list_item);
        ListView flightListView = (ListView) findViewById(R.id.add_flight_list);
        flightListView.setAdapter(flightAdapter);

        flightList = new ArrayList<Flight>();

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

        // Trigger the API to populate the Spinners
        api.getAllAirports(getAirportsCallback);
        api.getAllAirlines(getAirlinesCallback);
    }

    private void setOnClickListeners() {
        searchFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Get the airport and airline IDs
                int airport_id = airportList.get(airportSpinner.getSelectedItemPosition()).id;
                int airline_id = airlineList.get(airlineSpinner.getSelectedItemPosition()).id;

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

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void afterTextChanged(Editable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        // TODO Auto-generated method stub
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        // TODO Auto-generated method stub
    }
}
