package com.example.aniscope

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.databinding.ActivityGoThroughBinding
import com.example.aniscope.network.Repository
import com.example.aniscope.view_model.ApiViewModel
import com.example.aniscope.view_model.ApiViewModelFactory

class GoThroughActivity : AppCompatActivity() {

    private val viewModel by lazy {
        ViewModelProvider(this, ApiViewModelFactory(Repository()))[ApiViewModel::class.java]
    }

    var _binding: ActivityGoThroughBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoThroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel.getAnimeList(1).observe(this) {
            Log.e("anshul", "onCreate: ${it.body()}", )
        }

    }
}