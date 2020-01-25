package com.example.examen

import retrofit2.Call
import retrofit2.http.*

interface ObjAPI {
//    @DELETE("/recipe/{type}")
//    abstract fun deleteObject(@Header("Content-Type") content_type: String, @Path("type") type: String): Call<Object>
//
    @POST("/order")
    abstract fun addObject(@Body obj: Object, @Header("Content-Type") type: String): Call<Object>

//    @POST("/increment")
//    abstract fun updateObj(@Header("Content-Type") content_type: String, @Body obj: myObj): Call<Object>
//
    @PUT("/status/{id}")
    abstract fun postObject(@Header("Content-Type") type: String, @Path("id") id: Int): Call<Object>

    @GET("/pending")
    abstract fun getTypes(@Header("Content-Type") type: String): Call<List<Object>>

    @GET("/all")
    abstract fun getAll(@Header("Content-Type") type: String): Call<List<Object>>

    @GET("/orders/{id}")
    abstract fun getForUser(@Header("Content-Type") type: String, @Path("id") id:Int): Call<List<Object>>

}

