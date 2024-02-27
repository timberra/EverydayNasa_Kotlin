package com.ligagriezne.nasaeveryday
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface MyApi {
    @GET("https://api.nasa.gov/planetary/apod")

    fun getPostByDate(@Query("api_key") key: String,
                      @Query("date") date: String
    ): Call<NasaDataModel>
}