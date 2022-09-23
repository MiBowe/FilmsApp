package com.example.sandbox.MainActivity_Fragments.presentation.Adapter

data class FilmModelFromTop(
    val filmId: Int,
    val filmLength: String,
    val nameEn: String,
    val nameRu: String,
    val posterUrl: String,
    val posterUrlPreview: String,
    val rating: String,
    val ratingChange: Any,
    val ratingVoteCount: Int,
    val year: String
)