package com.example.aniscope.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class AnimeListResponse(
    @SerializedName("data")
    val animeList: List<AnimeData>? = null,
    @SerializedName("pagination")
    val pagination: Pagination? = null
): Serializable

data class AnimeData(
    @SerializedName("mal_id")
    val id: Int? = null,
    @SerializedName("background")
    val background: String? = null,
    @SerializedName("duration")
    val duration: String? = null,
    @SerializedName("episodes")
    val episodes: Int? = null,
    @SerializedName("genres")
    val genres: List<Genre>? = null,
    @SerializedName("producers")
    val producers: List<Genre>? = null,
    @SerializedName("images")
    val images: Images? = null,
    @SerializedName("rank")
    val rank: Int? = null,
    @SerializedName("rating")
    val rating: String? = null,
    @SerializedName("score")
    val score: Double? = null,
    @SerializedName("scored_by")
    val scoredBy: Int? = null,
    @SerializedName("season")
    val season: String? = null,
    @SerializedName("source")
    val source: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("synopsis")
    val synopsis: String? = null,
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("title_english")
    val titleEnglish: String? = null,
    @SerializedName("title_japanese")
    val titleJapanese: String? = null,
    @SerializedName("title_synonyms")
    val titleSynonyms: List<String>? = null,
    @SerializedName("titles")
    val titles: List<Title>? = null,
    @SerializedName("trailer")
    val trailer: Trailer? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("year")
    val year: Int? = null
): Serializable

data class Pagination(
    @SerializedName("current_page")
    val currentPage: Int? = null,
    @SerializedName("has_next_page")
    val hasNextPage: Boolean? = null,
    @SerializedName("items")
    val items: Items? = null,
    @SerializedName("last_visible_page")
    val lastVisiblePage: Int? = null
): Serializable

data class Items(
    @SerializedName("count")
    val count: Int? = null,
    @SerializedName("per_page")
    val perPage: Int? = null,
    @SerializedName("total")
    val total: Int? = null
): Serializable

data class Genre(
    @SerializedName("mal_id")
    val id: Int? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("type")
    val type: String? = null,
    @SerializedName("url")
    val url: String? = null
): Serializable

data class Trailer(
    @SerializedName("embed_url")
    val embedUrl: String? = null,
    @SerializedName("images")
    val images: Image? = null,
    @SerializedName("url")
    val url: String? = null,
    @SerializedName("youtube_id")
    val youtubeId: String? = null
): Serializable

data class Images(
    @SerializedName("jpg")
    val jpg: Image? = null,
    @SerializedName("webp")
    val webp: Image? = null
): Serializable

data class Image(
    @SerializedName("image_url")
    val imageUrl: String? = null,
    @SerializedName("large_image_url")
    val largeImageUrl: String? = null,
    @SerializedName("maximum_image_url")
    val maximumImageUrl: String? = null,
    @SerializedName("medium_image_url")
    val mediumImageUrl: String? = null,
    @SerializedName("small_image_url")
    val smallImageUrl: String? = null
): Serializable

data class Title(
    @SerializedName("title")
    val title: String? = null,
    @SerializedName("type")
    val type: String? = null
): Serializable