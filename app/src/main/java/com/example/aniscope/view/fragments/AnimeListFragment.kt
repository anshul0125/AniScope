package com.example.aniscope.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.aniscope.base.PaginationListenerLinear
import com.example.aniscope.databinding.FragmentAnimeListBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.network.Repository
import com.example.aniscope.view.adapter.AnimeListAdapter
import com.example.aniscope.view_model.ApiViewModel
import com.example.aniscope.view_model.ApiViewModelFactory
import com.example.aniscope.view_model.DatabaseViewModel
import com.example.aniscope.view_model.SharedViewModel

class AnimeListFragment : Fragment(), AnimeListAdapter.AnimeListCallback {

    var _binding: FragmentAnimeListBinding? = null
    val binding get() = _binding!!

    private val bookmarkList: ArrayList<AnimeData> = ArrayList()
    private var paginationListener: PaginationListenerLinear? = null
    private var currentAnimeList: MutableList<AnimeData> = mutableListOf()

    private val viewModel by lazy {
        ViewModelProvider(this, ApiViewModelFactory(Repository()))[ApiViewModel::class.java]
    }

    private val dbViewModel by lazy {
        ViewModelProvider(requireActivity())[DatabaseViewModel::class.java]
    }

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observer()
        getAnimeList()
    }

    private fun initUi() {
        binding.swipeToRefresh.setOnRefreshListener{
            viewModel.pageId = 1
            (binding.rvAnime.adapter as? AnimeListAdapter)?.resetList()
            getAnimeList()
        }
        paginationListener = object: PaginationListenerLinear(
            layoutManager = binding.rvAnime.layoutManager as LinearLayoutManager,
            pageSize = 2
        ) {
            override fun loadMoreItems() {
                binding.swipeToRefresh.isRefreshing = true
                getAnimeList()
            }
            override val isLastPage = viewModel.isLastPage
            override val isLoading = viewModel.isLoading
        }
    }

    private fun observer() {
        dbViewModel.daoObject?.getBookmarkedList()?.observe(viewLifecycleOwner) {
            bookmarkList.clear()
            bookmarkList.addAll(it)
            if (sharedViewModel.showBookmarkList.value == true) {
                (binding.rvAnime.adapter as? AnimeListAdapter)?.resetListAndUpdateList(bookmarkList)
            }
        }
        sharedViewModel.animeList.observe(viewLifecycleOwner) {
            if (sharedViewModel.showBookmarkList.value == false) {
                updateAnimeList(updatedList = it)
            } else {
                currentAnimeList.addAll(it)
            }
        }
        sharedViewModel.showBookmarkList.observe(viewLifecycleOwner){ showBookmarkList ->
            if(showBookmarkList) {
                binding.swipeToRefresh.isEnabled = false
                binding.rvAnime.clearOnScrollListeners()
                (binding.rvAnime.adapter as? AnimeListAdapter)?.apply {
                    currentAnimeList = list.toMutableList()
                    resetListAndUpdateList(bookmarkList)
                }
            } else {
                binding.swipeToRefresh.isEnabled = true
                paginationListener?.let { binding.rvAnime.addOnScrollListener(it) }
                (binding.rvAnime.adapter as? AnimeListAdapter)?.apply { resetListAndUpdateList(currentAnimeList) }
            }
        }
    }

    private fun updateAnimeList(updatedList: List<AnimeData>) {
        binding.rvAnime.visibility = View.VISIBLE
        if(binding.rvAnime.adapter is AnimeListAdapter) {
            (binding.rvAnime.adapter as AnimeListAdapter).updateList(newList = updatedList)
        } else {
            binding.rvAnime.adapter = AnimeListAdapter(list = (updatedList as? ArrayList) ?: arrayListOf(), this)
            paginationListener?.let { binding.rvAnime.addOnScrollListener(it) }
        }
    }

    private fun getAnimeList() {
        viewModel.getAnimeList(viewModel.pageId++).observe(viewLifecycleOwner) {
            if (it.isSuccessful && it.body() != null && it.code() == 200) {
                viewModel.isLoading = false
                viewModel.isLastPage = it.body()?.pagination == null || it.body()?.pagination?.hasNextPage == false
                binding.swipeToRefresh.isRefreshing = false
                binding.shimmerLayout.visibility = View.GONE
                sharedViewModel.setAnimeList(it.body()?.animeList.orEmpty())
            } else if (!it.isSuccessful) {
                viewModel.isLoading = false
                binding.swipeToRefresh.isRefreshing = false
                Toast.makeText(requireContext(), "Something went wrong", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onAnimeClicked(animeData: AnimeData) {
        val anime = AnimeDetailsBottomSheetFragment.newInstance(animeData)
        anime.show(childFragmentManager, AnimeDetailsBottomSheetFragment::class.java.simpleName)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null // Clear binding
    }

    companion object {
        @JvmStatic
        fun newInstance() = AnimeListFragment()
    }
}