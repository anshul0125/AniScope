package com.example.aniscope.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniscope.model.AnimeListResponse
import com.example.aniscope.network.Repository
import kotlinx.coroutines.launch
import retrofit2.Response

class ApiViewModel(val repository: Repository): ViewModel() {

    fun getAnimeList(page: Int): LiveData<Response<AnimeListResponse>> {
        return MutableLiveData<Response<AnimeListResponse>>().apply {
            viewModelScope.launch {
                postValue(repository.getAnimeList(page))
            }
        }
    }

    fun getAnimeDetail(id: Int): LiveData<Response<AnimeListResponse>> {
        return MutableLiveData<Response<AnimeListResponse>>().apply {
            viewModelScope.launch {
                postValue(repository.getAnimeDetail(id))
            }
        }
    }

}