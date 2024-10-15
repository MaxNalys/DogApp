package com.example.dogapi.data.repository

import com.example.dogapi.data.model.Dog
import com.example.dogapi.data.remote.DogApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DogRepository(private val apiService: DogApiService) {

    fun fetchRandomDogImage(onResult: (Dog?) -> Unit) {
        apiService.getRandomDogImage().enqueue(object : Callback<Dog> {
            override fun onResponse(call: Call<Dog>, response: Response<Dog>) {
                if (response.isSuccessful) {
                    onResult(response.body())
                } else {
                    onResult(null)
                }
            }

            override fun onFailure(call: Call<Dog>, t: Throwable) {
                onResult(null)
            }
        })
    }
}
