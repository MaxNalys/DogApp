package com.example.dogapi.data.remote

import com.example.dogapi.data.model.Dog
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface DogApiService {
    @GET("breeds/image/random")
    fun getRandomDogImage(): Call<Dog>

    companion object {
        private const val BASE_URL = "https://dog.ceo/api/"

        fun create(): DogApiService {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            return retrofit.create(DogApiService::class.java)
        }
    }
}
