package com.example.sandbox.MainActivity_Fragments.data.api

import androidx.annotation.IntRange
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("films")
    fun getFilms(
        @Query("page") @IntRange(from = 1) page: Int = 1,
        @Query("pageSize") @IntRange(from = 1, to = 20) pageSize : Int = 20

        ): Call<FilmListResponse>

}