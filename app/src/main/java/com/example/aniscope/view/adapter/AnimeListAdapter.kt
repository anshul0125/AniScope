package com.example.aniscope.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aniscope.R
import com.example.aniscope.databinding.ItemAnimeListBinding
import com.example.aniscope.model.AnimeData

class AnimeListAdapter(
    val list: ArrayList<AnimeData>,
    val listener: AnimeListCallback
): RecyclerView.Adapter<AnimeListAdapter.AnimeListViewHolder>() {

    inner class AnimeListViewHolder(val binding: ItemAnimeListBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AnimeData) {
            binding.root.context.let { context ->
                binding.apply {
                    root.setOnClickListener { listener.onAnimeClicked(data) }
                    tvTitle.text = data.title
                    tvRating.text = data.score.toString()
                    tvGenre.text = context.getString(R.string.genres_with_colon).plus(data.genres?.joinToString { it.name.orEmpty() } )
                    tvEpisodes.text = context.getString(R.string.episodes).plus(data.episodes.toString())
                    Glide.with(context)
                        .load(data.images?.jpg?.imageUrl.orEmpty())
                        .into(ivAnimeImage)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AnimeListViewHolder {
        val _binding = ItemAnimeListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AnimeListViewHolder(_binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: AnimeListViewHolder, position: Int) {
        holder.bind(list[position])
    }

    fun updateList(newList: List<AnimeData>) {
        val initialListSize = list.size
        list.addAll(newList)
        notifyItemRangeInserted(initialListSize, newList.size)
    }

    fun resetList() {
        val initialListSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, initialListSize)
    }

    fun resetListAndUpdateList(updatedList: List<AnimeData>) {
        val initialListSize = list.size
        list.clear()
        notifyItemRangeRemoved(0, initialListSize)
        list.addAll(updatedList)
        notifyItemRangeInserted(0, updatedList.size)
    }

    interface AnimeListCallback {
        fun onAnimeClicked(animeData: AnimeData)
    }

}