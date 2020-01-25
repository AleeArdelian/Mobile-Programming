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

public interface DoctorApi {
    @DELETE("/vethouse/api/v1/doctors/{id}")
    Call<Doctor> deleteDoctor(@Header("Content-Type") String content_type, @Path("email") String email);
    @POST("/vethouse/api/v1/doctors")
    Call<Doctor> addDoctor(@Body Doctor doctor,  @Header("Content-Type") String type);
    @PUT("/vethouse/api/v1/doctors/{id}")
    Call<Doctor> putDoctor(@Body Doctor doctor, @Path("id") int id, @Header("Content-Type") String type);
    @GET("/vethouse/api/v1/doctors")
    Call<List<Doctor>> getDoctors(@Header("Content-Type") String type);
}

