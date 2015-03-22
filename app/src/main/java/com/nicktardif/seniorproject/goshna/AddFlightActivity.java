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

import java.util.Calendar;
import java.util.Date;


public class AddFlightActivity extends ActionBarActivity implements TextWatcher {
    private AutoCompleteTextView airportAutoCompleteView;
    private AutoCompleteTextView flightAutoCompleteView;
    private Button searchFlightsButton;
    private Button backToSearchButton;
    private DatePicker datePicker;
    private RelativeLayout searchSection;
    private RelativeLayout addFlightSection;
    private FlightAdapter flightAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_flight);

        airportAutoCompleteView = (AutoCompleteTextView) findViewById(R.id.airport_auto_text_view);
        searchFlightsButton = (Button) findViewById(R.id.search_flights_button);
        backToSearchButton = (Button) findViewById(R.id.back_to_search_button);
        datePicker = (DatePicker) findViewById(R.id.add_flight_date);
        searchSection = (RelativeLayout) findViewById(R.id.search_section);
        addFlightSection = (RelativeLayout) findViewById(R.id.add_flight_section);

        String airports[] = getAllAirports();

        airportAutoCompleteView.addTextChangedListener(this);
        airportAutoCompleteView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, airports));

        flightAdapter = new FlightAdapter(getBaseContext(), R.layout.flight_list_item);
        ListView flightListView = (ListView) findViewById(R.id.add_flight_list);
        flightListView.setAdapter(flightAdapter);

        searchFlightsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String airport = airportAutoCompleteView.getText().toString();
                Date date = getDate(datePicker);

                Time departure = new Time();
                departure.set(0, 30, 4, 1, 3, 2014);

                flightAdapter.add(new Flight("AA", "AA133", new Date(), "TAM", "GNV", departure));
                flightAdapter.add(new Flight("DEL", "DEL133", new Date(), "ORL", "JAX", departure));

                // Toggle which section is seen
                searchSection.setVisibility(View.INVISIBLE);
                addFlightSection.setVisibility(View.VISIBLE);

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

    private String[] getAllAirports() {
        // TODO: Ask the server for all the airports
        String airports[] = { "TAM", "GNV", "ORL", "JAX" };
        return airports;
    }

    private String[] getFlights(String airport, Date date) {
        // TODO: Make request to the server to get all the flights
        String[] flights = { "L123", "AA235", "BD132", "OS873" };
        return flights;
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
