package com.jetpack.compose.learning.list.advancelist.model

data class MovieResponse(
    val page: Int,
    val results: List<Movie>
)

data class Movie(
    val id: Int,
    val poster_path: String,
    val original_language: String,
    val overview: String,
    val title: String,
    val original_title: String,
    val backdrop_path: String,
    val vote_average: String,
    val vote_count: String
)
