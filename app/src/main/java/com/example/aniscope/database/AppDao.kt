package com.example.aniscope.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.aniscope.model.AnimeData

@Dao
interface AppDao {

    @Query("SELECT * FROM anime")
    fun getBookmarkedList(): LiveData<List<AnimeData>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmark(bookmarkObject: AnimeData)

    @Query("DELETE FROM anime WHERE id = :id")
    suspend fun deleteBookmark(id: Int)

}

