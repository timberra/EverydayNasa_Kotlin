package com.ligagriezne.nasaeveryday

import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}

object ApiClient {
    val apiService: MyApi by lazy {
        RetrofitClient.retrofit.create(MyApi::class.java)
    }
}

interface NasaRepository {
    suspend fun getPostByDate(day: String): Result<NasaDataModel>
}

object NasaRepositoryImpl : NasaRepository {
    private val NASA_API_KEY = "tw3zjqcAbhFOy6J2qxf5ex1n4AthyXK2ssAHt5Us"

    private val store: MutableMap<String, NasaDataModel> = mutableMapOf()

    override suspend fun getPostByDate(day: String): Result<NasaDataModel> = try {
        // If we have the data in the store, return it immediately
        store[day]?.let { cachedPost -> return Success(cachedPost) }

        // Fetch the data from the network
        val response = ApiClient.apiService.getPostByDate(NASA_API_KEY, day)
        val body = response.body()

        if (response.isSuccessful && body != null) {
            store[day] = body
            Success(body)
        }
        else Error(response, null) // Most likely a http 4xx or 5xx errors

    } catch (e: Exception) {
        Error(null, e) // Network errors, etc
    }
}


sealed class Result<T>
data class Success<T>(val data: T) : Result<T>()
data class Error<T>(val response: Response<T>?, val t: Throwable?) : Result<T>()




