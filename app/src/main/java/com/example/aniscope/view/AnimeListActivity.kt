package com.example.aniscope.view

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.example.aniscope.base.PaginationListenerLinear
import com.example.aniscope.databinding.ActivityAnimeListBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.network.Repository
import com.example.aniscope.view.adapter.AnimeListAdapter
import com.example.aniscope.view_model.ApiViewModel
import com.example.aniscope.view_model.ApiViewModelFactory
import com.example.aniscope.view_model.SharedViewModel

class AnimeListActivity : AppCompatActivity() {

    var _binding: ActivityAnimeListBinding? = null
    val binding get() = _binding!!

    private val viewModel by lazy {
        ViewModelProvider(this, ApiViewModelFactory(Repository()))[ApiViewModel::class.java]
    }

    private val sharedViewModel by lazy {
        ViewModelProvider(this)[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityAnimeListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        observer()
        getAnimeList()
    }

    private fun observer() {
        sharedViewModel.animeList.observe(this) {
            updateAnimeList(updatedList = it)
        }
    }

    private fun updateAnimeList(updatedList: List<AnimeData>) {
        if(binding.rvAnime.adapter is AnimeListAdapter) {
            (binding.rvAnime.adapter as AnimeListAdapter).updateList(newList = updatedList)
        } else {
            binding.rvAnime.adapter = AnimeListAdapter(list = updatedList as ArrayList)
            binding.rvAnime.addOnScrollListener(object: PaginationListenerLinear(
                layoutManager = binding.rvAnime.layoutManager as LinearLayoutManager,
                pageSize = 1
            ) {
                override fun loadMoreItems() = getAnimeList()
                override val isLastPage = viewModel.isLastPage
                override val isLoading = viewModel.isLoading
            })
        }
    }

    private fun getAnimeList() {
        viewModel.getAnimeList(viewModel.pageId++).observe(this) {
            if (it.isSuccessful && it.body() != null && it.code() == 200) {
                viewModel.isLoading = false
                viewModel.isLastPage = it.body()?.pagination == null || it.body()?.pagination?.hasNextPage == false
                sharedViewModel.setAnimeList(it.body()?.animeList.orEmpty())
            }
        }
    }
}