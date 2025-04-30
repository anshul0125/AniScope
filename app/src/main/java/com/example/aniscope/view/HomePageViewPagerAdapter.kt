package com.example.aniscope.view

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.aniscope.R
import com.example.aniscope.view.fragments.AnimeListFragment

class HomePageViewPagerAdapter(
    private var bottomTabItems: List<Int>,
    fm: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fm, lifecycle) {

    override fun getItemCount() = bottomTabItems.size

    override fun createFragment(position: Int): Fragment {
        return when(bottomTabItems[position]) {
            R.id.home_item -> {
                AnimeListFragment.newInstance(showFavorites = false)
            }
            R.id.bookmark_item -> {
                AnimeListFragment.newInstance(showFavorites = true)
            }
            else -> {
                AnimeListFragment.newInstance(showFavorites = false)
            }
        }
    }

}