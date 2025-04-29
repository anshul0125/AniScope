package com.example.aniscope.view

import android.annotation.SuppressLint
import android.content.res.ColorStateList
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.R
import com.example.aniscope.databinding.ActivityAnimeListBinding
import com.example.aniscope.view.adapter.AnimeListAdapter
import com.example.aniscope.view.fragments.AnimeListFragment
import com.example.aniscope.view_model.SharedViewModel
import kotlin.jvm.internal.Intrinsics.Kotlin

class AnimeActivity : AppCompatActivity() {

    var _binding: ActivityAnimeListBinding? = null
    val binding get() = _binding!!

    private val sharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observer()
        setAnimeFragment()
    }

    private fun observer() {
        sharedViewModel.showBookmarkList.observe(this){ showBookmarkList ->
            updateBookmarkUiStatus(showBookmarkList)
        }
    }

    @SuppressLint("UseCompatTextViewDrawableApis")
    private fun updateBookmarkUiStatus(showBookmarkList: Boolean) {
        binding.tvBookmark.apply {
            if(showBookmarkList) {
                backgroundTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this@AnimeActivity, R.color.white)
                )
                setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(this@AnimeActivity, R.color.color_1F222A)
                    )
                )
                compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this@AnimeActivity, R.color.color_1F222A)
                )
            } else {
                backgroundTintList = null
                background = ContextCompat.getDrawable(this@AnimeActivity, R.drawable.bg_curved_stroke)
                setTextColor(
                    ColorStateList.valueOf(
                        ContextCompat.getColor(this@AnimeActivity, R.color.white)
                    )
                )
                compoundDrawableTintList = ColorStateList.valueOf(
                    ContextCompat.getColor(this@AnimeActivity, R.color.white)
                )
            }
        }
    }

    private fun initUi() {
        binding.tvBookmark.setOnClickListener{
            sharedViewModel.showBookmarkList.value?.let {
                sharedViewModel.showBookmarkList(!it)
            } ?: kotlin.run {
                sharedViewModel.showBookmarkList(true)
            }
        }
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