package com.nicktardif.seniorproject.goshna;

import com.nicktardif.seniorproject.goshna.ApiResponses.AirlineResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.AirportResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightIDResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.FlightResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.IdResponse;
import com.nicktardif.seniorproject.goshna.ApiResponses.MessageResponse;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;

/**
 * Created by tick on 4/7/15.
 */
public interface GoshnaApiService {
    final String baseURL = "/";

    @GET("/messages")
    void getMessages(Callback<MessageResponse> response);

    @GET("/user/messages/{user_id}")
    void getUserMessages(@Path("user_id") int user_id, Callback<MessageResponse> response);

    @GET("/airports")
    void getAllAirports(Callback<AirportResponse> response);

    @GET("/airlines")
    void getAllAirlines(Callback<AirlineResponse> response);

    @POST("/flights/find")
    void findFlights(@Body FlightSearchCriteria body, Callback<FlightResponse> response);

    @POST("/user")
    void createUserId(Callback<IdResponse> response);

    @GET("/flights/adduser/{user_id}/{flight_id}")
    void addUserToFlight(@Path("user_id") int user_id, @Path("flight_id") int flight_id, Callback<IdResponse> response);

    @GET("/flights/removeuser/{user_id}/{flight_id}")
    void removeUserFromFlight(@Path("user_id") int user_id, @Path("flight_id") int flight_id, Callback<IdResponse> response);

    @GET("/users/getflights/{user_id}")
    void getUserFlightIds(@Path("user_id") int user_id, Callback<FlightIDResponse> response);
}
