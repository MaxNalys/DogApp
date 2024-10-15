package com.example.dogapi.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.dogapi.data.model.Dog
import com.example.dogapi.data.repository.DogRepository

class DogViewModel(private val repository: DogRepository) : ViewModel() {

    private val _dogImage = MutableLiveData<String?>()
    val dogImage: LiveData<String?> get() = _dogImage

    fun getRandomDogImage() {
        repository.fetchRandomDogImage { dog ->
            _dogImage.value = dog?.message
        }
    }
}
