package com.example.aniscope.database

import androidx.room.TypeConverter
import com.example.aniscope.model.Genre
import com.example.aniscope.model.Images
import com.example.aniscope.model.Title
import com.example.aniscope.model.Trailer
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type


class Convertor {
    // Converter for ArrayList<String> (used for other fields, kept for compatibility)
    @TypeConverter
    fun fromString(value: String?): ArrayList<String> {
        val listType: Type = object : TypeToken<ArrayList<String?>?>() {}.type
        return Gson().fromJson(value, listType) ?: ArrayList()
    }

    @TypeConverter
    fun fromArrayList(list: ArrayList<String?>?): String {
        return Gson().toJson(list)
    }

    // Converter for List<Genre>
    @TypeConverter
    fun fromGenreList(genres: List<Genre>?): String {
        return Gson().toJson(genres)
    }

    @TypeConverter
    fun toGenreList(value: String?): List<Genre> {
        val listType: Type = object : TypeToken<List<Genre>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    // Converter for List<String> (for titleSynonyms)
    @TypeConverter
    fun fromStringList(strings: List<String>?): String {
        return Gson().toJson(strings)
    }

    @TypeConverter
    fun toStringList(value: String?): List<String> {
        val listType: Type = object : TypeToken<List<String>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    // Converter for List<Title> (for titles)
    @TypeConverter
    fun fromTitleList(titles: List<Title>?): String {
        return Gson().toJson(titles)
    }

    @TypeConverter
    fun toTitleList(value: String?): List<Title> {
        val listType: Type = object : TypeToken<List<Title>?>() {}.type
        return Gson().fromJson(value, listType) ?: emptyList()
    }

    // Converter for Trailer
    @TypeConverter
    fun fromTrailer(trailer: Trailer?): String {
        return Gson().toJson(trailer)
    }

    @TypeConverter
    fun toTrailer(value: String?): Trailer? {
        val type: Type = object : TypeToken<Trailer?>() {}.type
        return Gson().fromJson(value, type)
    }

    // Converter for Images
    @TypeConverter
    fun fromImages(images: Images?): String {
        return Gson().toJson(images)
    }

    @TypeConverter
    fun toImages(value: String?): Images? {
        val type: Type = object : TypeToken<Images?>() {}.type
        return Gson().fromJson(value, type)
    }
}