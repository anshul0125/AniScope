package com.example.aniscope.base

import android.app.Application
import android.content.Context
import com.example.aniscope.database.AppDatabase

class BaseApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        context = applicationContext
        AppDatabase.getDbInstance(this)
    }

    companion object {
        var context: Context? = null
    }
}