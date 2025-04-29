package com.example.aniscope.view_model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aniscope.base.BaseApplication
import com.example.aniscope.database.AppDao
import com.example.aniscope.database.AppDatabase
import com.example.aniscope.model.AnimeData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DatabaseViewModel: ViewModel() {

    var daoObject: AppDao? = null

    init {
        daoObject = BaseApplication.context?.let { AppDatabase.getDbInstance(it).getAppDao() }
    }

    fun bookmarkObject(bookmarkObject: AnimeData) {
        viewModelScope.launch(Dispatchers.IO) {
            daoObject?.insertBookmark(bookmarkObject)
        }
    }

    fun deleteBookmark(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            daoObject?.deleteBookmark(id)
        }
    }

}