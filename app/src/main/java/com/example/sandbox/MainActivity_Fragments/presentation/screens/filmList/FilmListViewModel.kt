package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import com.example.sandbox.MainActivity_Fragments.Domain.api.App

import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

open class FilmListViewModel: ViewModel() {
    val filmList: LiveData<MutableList<FilmItem>>

    init {
        Log.d("FilmsVXM", this.toString())

        filmList = MutableLiveData<MutableList<FilmItem>>()
        filmList.value = mutableListOf<FilmItem>()

        getFilmList()
    }

    private fun getFilmList(page: Int = 1) {
        App.instance.api.getFilms(page)
            .enqueue(object : Callback<FilmListResponse> {
                override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<FilmListResponse>,
                    response: Response<FilmListResponse>
                )   {
                        filmList.value?.clear()
                        if (response.isSuccessful) {
                            response.body()?.items
                                ?.forEach {
                                    filmList.value?.add(
                                        FilmItem(
                                            it.posterUrlPreview,
                                            it.kinopoiskId,
                                            it.nameEn,
                                            it.nameRu
                                        )
                                    )
                                }
                        }
                    }
            })
    }



}