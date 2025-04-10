package com.example.aniscope.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.aniscope.databinding.ActivityAnimeListBinding
import com.example.aniscope.view.fragments.AnimeListFragment

class AnimeActivity : AppCompatActivity() {

    var _binding: ActivityAnimeListBinding? = null
    val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        setAnimeFragment()
    }

    private fun initUi() {
        binding.ivBackPress.setOnClickListener{
            onBackPressed()
        }
    }

    private fun setAnimeFragment() {
        supportFragmentManager.beginTransaction()
            .add(binding.fragmentContainer.id, AnimeListFragment.newInstance(), AnimeListFragment::class.java.simpleName)
            .addToBackStack(AnimeListFragment::class.java.simpleName)
            .commit()
    }

    override fun onBackPressed() {
        if(supportFragmentManager.backStackEntryCount > 1) {
            super.onBackPressed()
        } else {
            finish()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Clear binding
    }


}