package com.example.sandbox.MainActivity_Fragments.data.api

import androidx.annotation.IntRange
import com.example.sandbox.MainActivity_Fragments.data.api.PostRequest.RetrofitPostRequestModel
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem
import retrofit2.Call
import retrofit2.http.*

interface Api {

    @GET("films")
    fun getFilms(
        @Query("page") @IntRange(from = 1) page: Int? = 1,
        ): Call<FilmListResponse>

    @GET("films/{id}")
    fun getMovieDetails(
        @Path("id") id: Int
    ): Call<KinopoiskFilmItem>

    @POST("fcm/send")
    fun postNotif(@Body requestModel: RetrofitPostRequestModel):
            Call<RetrofitPostRequestModel>
}