package com.example.sandbox.MainActivity_Fragments.data.repository.films

import android.util.Log
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors



object FilmsRepositoryImpl: FilmsRepository {

    override fun getFilms(page: Int, successResult: (films: List<FilmItem>) -> Unit) {

        App.instance.api.getFilms(page)
            .enqueue(object : Callback<FilmListResponse> {
                override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                    Log.d("OOPS! ------------------------------------------------->", "Api request is not success")
                }
                override fun onResponse(
                    call: Call<FilmListResponse>,
                    response: Response<FilmListResponse>
                ) {
                    if (response.isSuccessful) {
                        response.body()?.items
                            ?.map {
                                FilmItem(
                                    poster = it!!.posterUrlPreview,
                                    posterToolbar = it!!.posterUrl,
                                    id = it!!.kinopoiskId,
                                    title = it!!.nameRu,
                                    subtitle = it.shortDescription,
                                    pageNumber = page
                                )
                            }?.let {
                                Executors.newSingleThreadExecutor().execute {
                                    App.instance.appDB?.getFilmDao()?.insertFilms(it)
                                }
                                successResult.invoke(it)
                            }
                    }
                }
            })
    }

    override fun getFilmsFromRoom(page: Int, listDB: (films: List<FilmItem>) -> Unit) {
        val task = Runnable {
            val result = App.instance.appDB?.let {
                it.getFilmDao().getFilmsByPage(page)
            }
            result?.let {
                if (result.isNotEmpty()) {
                    result.map {
                        FilmItem(
                            poster = it!!.poster,
                            posterToolbar = it!!.posterToolbar,
                            id = it!!.id,
                            title = it.title,
                            subtitle = it.subtitle,
                            idDB = it.idDB,
                            isFavorite = it.isFavorite,
                            pageNumber = it.pageNumber
                        )
                    }.let { listDB.invoke(it) }
                } else {
                    listDB.invoke(emptyList())
                }
            }
        }
        Executors.newSingleThreadExecutor().submit(task)
    }

    override fun getMovieDetails(id: Int, successResult: (film :FilmItem?) -> Unit) {
        val result = App.instance.api.getMovieDetails(id)
            .enqueue(object : Callback<KinopoiskFilmItem> {
                override fun onFailure(call: Call<KinopoiskFilmItem>, t: Throwable) {
                    Log.d("OOPS! ------------------------------------------------->", "Api request is not success")
                }
                override fun onResponse(
                    call: Call<KinopoiskFilmItem>,
                    response: Response<KinopoiskFilmItem>
                ){

                    if (response.isSuccessful){
                        response.body()?.let {
                            FilmItem(
                                poster = it.posterUrlPreview,
                                posterToolbar = it.posterUrl,
                                title = it.nameRu,
                                subtitle = it.description,
                                id = it.kinopoiskId,
                                idDB = 0,
                                pageNumber = 0
                            )
                        }.let {
                            successResult.invoke(it) }
                    }
                }
            })
    }

    override fun getFavoriteFilms(films: (filmList: List<FilmItem>) -> Unit) {
        val task = Runnable {
            val result = App.instance.appDB?.let {
                it.getFilmDao().getAll()
            }
            result?.let {
                if (result.isNotEmpty()) {
                    result.map {
                        FilmItem(
                            poster = it!!.poster,
                            posterToolbar = it!!.posterToolbar,
                            id = it!!.id,
                            title = it!!.title,
                            subtitle = it!!.subtitle,
                            idDB = it.idDB,
                            isFavorite = it.isFavorite,
                            pageNumber = it.pageNumber
                        )
                    }.let { films.invoke(it) }
                } else {
                    films.invoke(emptyList())
                }
            }
        }
        Executors.newSingleThreadExecutor().submit(task)
    }
}