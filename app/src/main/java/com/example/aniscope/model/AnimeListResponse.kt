package com.example.aniscope.model

import com.google.gson.annotations.SerializedName

data class AnimeListResponse(
    @SerializedName("data")
    val animeList: List<AnimeData>,
    @SerializedName("pagination")
    val pagination: Pagination
)

data class AnimeData(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("background")
    val background: String,
    @SerializedName("duration")
    val duration: String,
    @SerializedName("episodes")
    val episodes: Int,
    @SerializedName("genres")
    val genres: List<Genre>,
    @SerializedName("images")
    val images: Images,
    @SerializedName("rank")
    val rank: Int,
    @SerializedName("rating")
    val rating: String,
    @SerializedName("score")
    val score: Double,
    @SerializedName("scored_by")
    val scoredBy: Int,
    @SerializedName("season")
    val season: String,
    @SerializedName("source")
    val source: String,
    @SerializedName("status")
    val status: String,
    @SerializedName("synopsis")
    val synopsis: String,
    @SerializedName("title")
    val title: String,
    @SerializedName("title_english")
    val titleEnglish: String,
    @SerializedName("title_japanese")
    val titleJapanese: String,
    @SerializedName("title_synonyms")
    val titleSynonyms: List<String>,
    @SerializedName("titles")
    val titles: List<Title>,
    @SerializedName("trailer")
    val trailer: Trailer,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String,
    @SerializedName("year")
    val year: Int
)

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean,
    @SerializedName("items")
    val items: Items,
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int
)

data class Items(
    @SerializedName("count")
    val count: Int,
    @SerializedName("per_page")
    val perPage: Int,
    @SerializedName("total")
    val total: Int
)

data class Genre(
    @SerializedName("mal_id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("type")
    val type: String,
    @SerializedName("url")
    val url: String
)

data class Trailer(
    @SerializedName("embed_url")
    val embedUrl: String,
    @SerializedName("images")
    val images: Image,
    @SerializedName("url")
    val url: String,
    @SerializedName("youtube_id")
    val youtubeId: String
)

data class Images(
    @SerializedName("jpg")
    val jpg: Image,
    @SerializedName("webp")
    val webp: Image
)

data class Image(
    @SerializedName("image_url")
    val imageUrl: String,
    @SerializedName("large_image_url")
    val largeImageUrl: String,
    @SerializedName("maximum_image_url")
    val maximumImageUrl: String,
    @SerializedName("medium_image_url")
    val mediumImageUrl: String,
    @SerializedName("small_image_url")
    val smallImageUrl: String
)

data class Title(
    @SerializedName("title")
    val title: String,
    @SerializedName("type")
    val type: String
)