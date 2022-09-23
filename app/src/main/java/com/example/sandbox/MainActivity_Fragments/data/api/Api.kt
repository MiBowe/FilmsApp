package com.example.sandbox.MainActivity_Fragments.data.api

import androidx.annotation.IntRange
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {

    @GET("films")
    fun getFilms(
        @Query("page") @IntRange(from = 1) page: Int? = 1,
        ): Call<FilmListResponse>

    @GET("films/{id}")
    fun getMovieDetails(
        @Path("id") id: Int
    ): Call<KinopoiskFilmItem>
}