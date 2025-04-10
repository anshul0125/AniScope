package com.example.aniscope.view

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aniscope.databinding.ActivityGoThroughBinding

class GoThroughActivity : AppCompatActivity() {

    var _binding: ActivityGoThroughBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityGoThroughBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvGetStartedButton.setOnClickListener {
            val intent = Intent(this, AnimeActivity::class.java)
            startActivity(intent)
        }

    }
}