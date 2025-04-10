package com.example.aniscope.view.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.aniscope.databinding.ItemMoreLikeThisBinding
import com.example.aniscope.model.AnimeData
import com.example.aniscope.view.adapter.AnimeListAdapter.AnimeListCallback

class MoreLikeThisAnimeAdapter(
    val list: List<AnimeData>,
    val listener: AnimeListCallback
): RecyclerView.Adapter<MoreLikeThisAnimeAdapter.MoreLikeThisAnimeViewHolder>() {
    inner class MoreLikeThisAnimeViewHolder(val binding: ItemMoreLikeThisBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(data: AnimeData) {
            binding.root.setOnClickListener { listener.onAnimeClicked(data) }
            Glide.with(binding.root.context)
                .load(data.images?.jpg?.imageUrl.orEmpty())
                .into(binding.ivMoreAnime)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MoreLikeThisAnimeViewHolder {
        val binding = ItemMoreLikeThisBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MoreLikeThisAnimeViewHolder(binding)
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: MoreLikeThisAnimeViewHolder, position: Int) {
        holder.bind(list[position])
    }
}