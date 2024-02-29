package com.ligagriezne.nasaeveryday
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface MyApi {
    @GET("https://api.nasa.gov/planetary/apod")

    suspend fun getPostByDate(@Query("api_key") key: String,
                      @Query("date") date: String
    ): Response<NasaDataModel>
}