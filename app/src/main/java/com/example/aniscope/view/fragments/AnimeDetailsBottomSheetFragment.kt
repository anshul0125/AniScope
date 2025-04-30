package com.example.aniscope.view.fragments

import android.content.Context
import android.content.DialogInterface
import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aniscope.R
import com.example.aniscope.databinding.FragmentAnimeDetailsBottomSheetBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.view.adapter.AnimeListAdapter
import com.example.aniscope.view.adapter.MoreLikeThisAnimeAdapter
import com.example.aniscope.view_model.DatabaseViewModel
import com.example.aniscope.view_model.SharedViewModel
import com.google.android.material.R.id.design_bottom_sheet
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAnimeDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!

    private val bookmarkList = ArrayList<AnimeData>()
    private val TAG = this.javaClass.simpleName
    private var animeData: AnimeData? = null

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
    }

    private val viewModel by lazy {
        ViewModelProvider(requireActivity())[DatabaseViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            animeData = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getSerializable(EXTRA_KEY_ANIME_SCREEN_ID, AnimeData::class.java)
            } else {
                it.getSerializable(EXTRA_KEY_ANIME_SCREEN_ID) as AnimeData
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentAnimeDetailsBottomSheetBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUi()
        observer()
        animeData?.let { setAnimeDetails(data = it) }
    }

    private fun observer() {
        viewModel.daoObject?.getBookmarkedList()?.observe(viewLifecycleOwner){
            bookmarkList.clear()
            bookmarkList.addAll(it)
            setBookmarkUi()
        }
    }

    private fun setBookmarkUi() {
        if(bookmarkList.find { it.id == animeData?.id } != null){
            setBookmarkDrawable(isFilled = true)
        } else {
            setBookmarkDrawable(isFilled = false)
        }
    }

    private fun initUi() {
        binding.ivCross.setOnClickListener { dismiss() }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)
            val windowHeight = Resources.getSystem().displayMetrics.heightPixels
            it.layoutParams.height = windowHeight
            it.requestLayout()
            behavior.skipCollapsed = true
            behavior.isHideable = true
            behavior.isDraggable = false
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun setAnimeDetails(data: AnimeData) {
        data.apply {
            binding.ivBookmark.setOnClickListener {
                if(bookmarkList.find { bookmark -> bookmark.id == animeData?.id } != null){
                    deleteBookmark()
                } else {
                    addToBookmark()
                }
            }

            title?.let { title ->
                binding.tvTitle.text = title
            } ?: kotlin.run {
                binding.tvTitle.visibility = View.GONE
            }

            score?.let { score ->
                binding.tvRating.text = getString(R.string.rated_with_collon).plus(score.toString())
            } ?: kotlin.run {
                binding.ivArrow.visibility = View.GONE
                binding.tvRating.visibility = View.GONE
            }

            year?.let { year ->
                binding.tvYear.text = year.toString()
            } ?: kotlin.run {
                binding.tvYear.visibility = View.GONE
                binding.ivArrow.visibility = View.GONE
            }

            episodes?.let { episodes ->
                binding.tvEpisodes.text = getString(R.string.episodes_with_collon, episodes.toString())
            } ?: kotlin.run {
                binding.tvEpisodes.visibility = View.GONE
            }

            genres?.let { genres ->
                binding.tvGenre.text = context?.getString(R.string.genres_with_colon).plus(genres.joinToString { it.name.orEmpty() } )
            } ?: kotlin.run {
                binding.tvGenre.visibility = View.GONE
            }

            synopsis?.let { synopsis ->
                binding.tvDescription.text = synopsis
            } ?: kotlin.run {
                binding.tvDescription.visibility = View.GONE
            }

            producers?.joinToString { it.name.orEmpty() }?.let { producersComaSeparated ->
                if(producersComaSeparated.isEmpty()) {
                    binding.tvProducers.visibility = View.GONE
                } else {
                    binding.tvProducers.text = getString(R.string.producers_with_colon).plus(producersComaSeparated)
                }
            }

            trailer?.youtubeId?.let { youtubeId ->
                Handler(Looper.getMainLooper()).postDelayed({
                    if (isAdded && _binding != null) binding.ypAnimeTrailer.addYouTubePlayerListener(setYoutubePlayer())
                }, 100) // 100ms delay
            } ?: run {
                binding.ypAnimeTrailer.visibility = View.GONE
                images?.jpg?.largeImageUrl?.let {
                    binding.ivAnimeImage.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(it).into(binding.ivAnimeImage)
                }
            }
            addMoreLikeThis()
        }
    }

    private fun setBookmarkDrawable(isFilled: Boolean) {
        if(isFilled){
            binding.ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark_filled)
            )
        } else {
            binding.ivBookmark.setImageDrawable(
                ContextCompat.getDrawable(requireContext(), R.drawable.ic_bookmark_unfilled)
            )
        }
    }

    private fun addMoreLikeThis() {
        val shuffledList = sharedViewModel.animeList.value?.shuffled()?.take(5)
        binding.rvMoreLikeThese.adapter = MoreLikeThisAnimeAdapter(
            list = shuffledList.orEmpty(),
            listener = object : AnimeListAdapter.AnimeListCallback {
                override fun onAnimeClicked(animeData: AnimeData) {
                    val anime = newInstance(animeData)
                    anime.show(childFragmentManager, AnimeDetailsBottomSheetFragment::class.java.simpleName)
                }
            }
        )
    }

    private fun AnimeData.deleteBookmark() {
        id?.let { id -> viewModel.deleteBookmark(id) }
    }

    private fun AnimeData.addToBookmark() {
        viewModel.bookmarkObject(this)
    }

    fun Context.showToast(message: String, isLongToast: Boolean = false) {
        val duration = if (isLongToast) Toast.LENGTH_LONG else Toast.LENGTH_SHORT
        Toast.makeText(this, message, duration).show()
    }

    private fun AnimeData.setYoutubePlayer() =
        object : AbstractYouTubePlayerListener() {
            override fun onReady(youTubePlayer: YouTubePlayer) {
                Log.e(TAG, "YouTube Player Ready", )
                youTubePlayer.cueVideo(trailer?.youtubeId.orEmpty(), 0f)
            }

            override fun onError(
                youTubePlayer: YouTubePlayer,
                error: PlayerConstants.PlayerError
            ) {
                Log.e(TAG, "YouTube Player Error: $error")
                if (isAdded && _binding != null) {
                    binding.ypAnimeTrailer.visibility = View.GONE
                    images?.jpg?.largeImageUrl?.let {
                        binding.ivAnimeImage.visibility = View.VISIBLE
                        Glide.with(requireContext()).load(it).into(binding.ivAnimeImage)
                    }
                }
            }
        }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.ypAnimeTrailer.release() // Release the player
        _binding = null // Clear binding
    }

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        binding.ypAnimeTrailer.release() // Extra release on dismiss
    }

    companion object {

        const val EXTRA_KEY_ANIME_SCREEN_ID = "ANIME_SCREEN_ID"
        @JvmStatic
        fun newInstance(animeId: AnimeData) =
            AnimeDetailsBottomSheetFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(EXTRA_KEY_ANIME_SCREEN_ID, animeId)
                }
            }
    }
}