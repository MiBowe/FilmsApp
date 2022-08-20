package com.example.sandbox.MainActivity_Fragments.Domain.api

import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("films")
    fun getFilms(@Query("page") page: Int): Call<FilmListResponse>

}