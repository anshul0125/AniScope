package com.example.aniscope.view.fragments

import android.content.res.Resources
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.aniscope.R
import com.example.aniscope.databinding.FragmentAnimeDetailsBottomSheetBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.view.adapter.AnimeListAdapter
import com.example.aniscope.view.adapter.MoreLikeThisAnimeAdapter
import com.example.aniscope.view_model.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

class AnimeDetailsBottomSheetFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentAnimeDetailsBottomSheetBinding? = null
    private val binding get() = _binding!!
    private var animeData: AnimeData? = null
    private val TAG = this.javaClass.simpleName

    private val sharedViewModel by lazy {
        ViewModelProvider(requireActivity())[SharedViewModel::class.java]
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
        observer()
        animeData?.let { setAnimeDetails(data = it) }
    }

    override fun onStart() {
        super.onStart()
        val dialog = dialog as? BottomSheetDialog
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)

        bottomSheet?.let {
            val behavior = BottomSheetBehavior.from(it)

            // Set height to 80% of screen
            val windowHeight = Resources.getSystem().displayMetrics.heightPixels
            val desiredHeight = (windowHeight * 0.95).toInt()

            it.layoutParams.height = desiredHeight
            it.requestLayout()
            behavior.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }

    private fun observer() {
//        sharedViewModel.selectedAnime.observe(viewLifecycleOwner){
//            if(it != null) {
//                setAnimeDetails(data = it)
//            }
//        }
    }

    private fun setAnimeDetails(data: AnimeData) {
        data.apply {

            title?.let { title ->
                binding.tvTitle.text = title
            } ?: kotlin.run {
                binding.tvTitle.visibility = View.GONE
            }

            score?.let { score ->
                binding.tvRating.text = "Rated: ".plus(score.toString())
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
                binding.tvEpisodes.text = "Episodes: $episodes"
            } ?: kotlin.run {
                binding.tvEpisodes.visibility = View.GONE
            }

            genres?.let { genres ->
                binding.tvGenre.text = context?.getString(R.string.genres_with_collon).plus(data.genres?.joinToString { it.name.orEmpty() } )
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
                    binding.tvProducers.text = "Producers: ".plus(producersComaSeparated)
                }
            }

            trailer?.youtubeId?.let { youtubeId ->
                binding.ypAnimeTrailer.addYouTubePlayerListener(object : AbstractYouTubePlayerListener() {
                    override fun onReady(youTubePlayer: YouTubePlayer) {
                        youTubePlayer.cueVideo(youtubeId, 0f)
                    }
                    override fun onError(
                        youTubePlayer: YouTubePlayer,
                        error: PlayerConstants.PlayerError
                    ) {
                        Log.e(TAG, "onError: $error", )
                        binding.ypAnimeTrailer.visibility = View.GONE
                        images?.jpg?.largeImageUrl?.let {
                            binding.ivAnimeImage.visibility = View.VISIBLE
                            Glide.with(requireContext()).load(it).into(binding.ivAnimeImage)
                        }
                    }
                })
            } ?: kotlin.run {
                binding.ypAnimeTrailer.visibility = View.GONE
                images?.jpg?.largeImageUrl?.let {
                    binding.ivAnimeImage.visibility = View.VISIBLE
                    Glide.with(requireContext()).load(it).into(binding.ivAnimeImage)
                }
            }
            addMoreLikeThis()
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