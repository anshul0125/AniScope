package com.example.aniscope.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aniscope.model.AnimeData

class SharedViewModel: ViewModel() {

    private val _animeList = MutableLiveData<List<AnimeData>>()
    val animeList: LiveData<List<AnimeData>> = _animeList

    private val _selectedAnime = MutableLiveData<AnimeData>()
    val selectedAnime: LiveData<AnimeData> = _selectedAnime

    fun setAnimeList(list: List<AnimeData>) {
        _animeList.postValue(list)
    }

    fun setAnime(animeData: AnimeData) {
        _selectedAnime.postValue(animeData)
    }

}