package com.example.aniscope.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.aniscope.model.AnimeData

@TypeConverters(Convertor::class)
@Database(
    entities = [
        AnimeData::class,
    ], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getAppDao(): AppDao

    companion object {
        private const val databaseName = "database"
        private var dbInstance: AppDatabase? = null

        @Synchronized
        fun getDbInstance(context: Context): AppDatabase {
            if (dbInstance == null) {
                dbInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    databaseName
                ).fallbackToDestructiveMigration().build()
            }
            return dbInstance!!
        }
    }
}