package com.example.vet_house;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserAPI {
//    @DELETE("/vethouse/api/v1/users")
//    Call<User> deleteUser(@Header("Content-Type") String content_type, String email);
    @POST("/vethouse/api/v1/users")
    Call<User> addUser(@Body User user, @Header("Content-Type") String type);
//    @PUT("/vethouse/api/v1/users")
//    Call<User> putUser(@Body User user, String email, @Header("Content-Type") String type);
    @GET("/vethouse/api/v1/doctors")
    Call<List<User>> getUsers(@Header("Content-Type") String type);
}
