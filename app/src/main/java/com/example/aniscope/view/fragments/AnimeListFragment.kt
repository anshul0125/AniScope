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
    private val animeList: ArrayList<AnimeData> = ArrayList()
    private var paginationListener: PaginationListenerLinear? = null
    private var showFavorites = false

    private val viewModel by lazy {
        ViewModelProvider(this, ApiViewModelFactory(Repository()))[ApiViewModel::class.java]
    }

    private val dbViewModel by lazy {
        ViewModelProvider(this)[DatabaseViewModel::class.java]
    }

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showFavorites = arguments?.getBoolean(ARGS_SHOW_BOOKMARK) ?: false
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
        initData()
        initUi()
    }

    private fun initData() {
        paginationListener = object: PaginationListenerLinear(
            layoutManager = binding.rvAnime.layoutManager as LinearLayoutManager,
            pageSize = 2
        ) {
            override fun loadMoreItems() = getAnimeList()
            override val isLastPage = viewModel.isLastPage
            override val isLoading = viewModel.isLoading
        }
    }

    private fun initUi() {
        binding.rvAnime.adapter = AnimeListAdapter(this)
        if(!showFavorites) paginationListener?.let {
            binding.rvAnime.addOnScrollListener(it)
        }
        binding.swipeToRefresh.setOnRefreshListener{
            viewModel.pageId = 1
            animeList.clear()
            getAnimeList()
        }
        if(!showFavorites) {
            getAnimeList()
            observer()
        } else {
            getBookmarkList()
            binding.swipeToRefresh.isEnabled = false
        }
    }

    private fun getBookmarkList() {
        dbViewModel.daoObject?.getBookmarkedList()?.observe(viewLifecycleOwner) {
            bookmarkList.clear()
            bookmarkList.addAll(it)
            updateAnimeList(updatedList = bookmarkList)
        }
    }


    private fun observer() {
        sharedViewModel.animeList.observe(viewLifecycleOwner) {
            animeList.addAll(it)
            updateAnimeList(updatedList = animeList)
        }
    }

    private fun updateAnimeList(updatedList: List<AnimeData>) {
        binding.rvAnime.visibility = View.VISIBLE
        binding.shimmerLayout.visibility = View.GONE
        (binding.rvAnime.adapter as AnimeListAdapter).saveData(newList = updatedList)
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
        const val ARGS_SHOW_BOOKMARK = "SHOW_BOOKMARK"
        @JvmStatic
        fun newInstance(showFavorites: Boolean) = AnimeListFragment().apply {
            arguments = Bundle().apply {
                putBoolean(ARGS_SHOW_BOOKMARK, showFavorites)
            }
        }
    }
}