package com.example.vet_house;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AppointmentAPI {
    @DELETE("/vethouse/api/v1/appointment/{appId}")
    Call<Appointment> deleteAppointment(@Header("Content-Type") String content_type, @Path("appId") int id);
    @POST("/vethouse/api/v1/appointments")
    Call<Appointment> addAppointment(@Body Appointment appointment, @Header("Content-Type") String type);
    @PUT("/vethouse/api/v1/appointments/{appId}")
    Call<Appointment> putAppointment(@Body Appointment appointment, @Path("appId") int id, @Header("Content-Type") String type);
    @GET("/vethouse/api/v1/appointments")
    Call<List<Appointment>> getAppointments(@Header("Content-Type") String type);
}
