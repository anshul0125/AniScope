package com.example.aniscope.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.aniscope.R
import com.example.aniscope.databinding.ActivityAnimeListBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.view_model.DatabaseViewModel
import com.example.aniscope.view_model.SharedViewModel

class AnimeActivity : AppCompatActivity() {

    var _binding: ActivityAnimeListBinding? = null
    val binding get() = _binding!!

    private val bookmarkList: ArrayList<AnimeData> = ArrayList()

    private val sharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(this)[DatabaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initUi()
        observer()
    }

    private fun observer() {
        viewModel.daoObject?.getBookmarkedList()?.observe(this){
            bookmarkList.clear()
            bookmarkList.addAll(it)
            setBookmarkCount(count = it.size)
        }
    }

    private fun setBookmarkCount(count: Int) {
        binding.bottomNavigationView.getOrCreateBadge(R.id.bookmark_item).apply {
            number = count
            backgroundColor = ContextCompat.getColor(this@AnimeActivity, R.color.color_6C5DD3)
            badgeTextColor = ContextCompat.getColor(this@AnimeActivity, R.color.white)
        }
    }

    private fun initUi() {
        binding.ivBackPress.setOnClickListener{
            onBackPressed()
        }
        setBottomNavigation()
        setHomepageViewPager()
    }

    private fun setHomepageViewPager() {
        binding.homePageViewPager.setOffscreenPageLimit(1)
        binding.homePageViewPager.setUserInputEnabled(false)
        binding.homePageViewPager.setPageTransformer(null)
        binding.homePageViewPager.adapter = HomePageViewPagerAdapter(
            bottomTabItems = listOf(R.id.home_item, R.id.bookmark_item),
            fm = supportFragmentManager,
            lifecycle = lifecycle,
        )
    }

    private fun setBottomNavigation() {
        binding.bottomNavigationView.menu.clear()
        binding.bottomNavigationView.inflateMenu(R.menu.menu_home)
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.home_item -> {
                    binding.tvTitle.text = getString(R.string.discover_animes)
                    binding.homePageViewPager.setCurrentItem(0, true)
                }
                R.id.bookmark_item -> {
                    binding.tvTitle.text = getString(R.string.your_favourites)
                    binding.homePageViewPager.setCurrentItem(1, true)
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    override fun onBackPressed() {
        if (binding.bottomNavigationView.selectedItemId == R.id.bookmark_item) {
            binding.bottomNavigationView.selectedItemId = R.id.home_item
            return
        } else {
            super.onBackPressed()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding = null // Clear binding
    }


}