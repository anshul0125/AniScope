package com.example.aniscope.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.network.Repository

class ApiViewModelFactory(val repository: Repository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(ApiViewModel::class.java)) {
            ApiViewModel(repository) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }

    }

}