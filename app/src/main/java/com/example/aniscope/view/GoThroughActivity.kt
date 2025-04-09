package com.example.aniscope.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.databinding.ActivityGoThroughBinding
import com.example.aniscope.network.Repository
import com.example.aniscope.view_model.ApiViewModel
import com.example.aniscope.view_model.ApiViewModelFactory

class GoThroughActivity : AppCompatActivity() {

    var _binding: ActivityGoThroughBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoThroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGetStartedButton.setOnClickListener {
            val intent = Intent(this, AnimeListActivity::class.java)
            startActivity(intent)
        }

    }
}