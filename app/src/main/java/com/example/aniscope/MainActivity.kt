package com.example.aniscope

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.network.Repository
import com.example.aniscope.view_model.ApiViewModel
import com.example.aniscope.view_model.ApiViewModelFactory

class MainActivity : AppCompatActivity() {

    val viewModel by lazy {
        ViewModelProvider(this, ApiViewModelFactory(Repository()))[ApiViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.getAnimeList(1).observe(this) {
            Log.e("anshul", "onCreate: ${it.body()}", )
        }

    }
}