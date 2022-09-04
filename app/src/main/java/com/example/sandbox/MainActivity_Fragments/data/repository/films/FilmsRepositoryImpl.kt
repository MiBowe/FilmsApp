package com.example.sandbox.MainActivity_Fragments.data.repository.films

import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.concurrent.Executors



object FilmsRepositoryImpl: FilmsRepository {

    override fun getFilms(successResult: (films: List<FilmItem>) -> Unit) {

        App.instance.api.getFilms()
            .enqueue(object : Callback<FilmListResponse> {
                override fun onFailure(call: Call<FilmListResponse>, t: Throwable) {
                }

                override fun onResponse(
                    call: Call<FilmListResponse>,
                    response: Response<FilmListResponse>
                )   {
                    if (response.isSuccessful) {
                        response.body()?.items
                            ?.map {
                                FilmItem(
                                    it.posterUrlPreview,
                                    it.posterUrl,
                                    it.kinopoiskId,
                                    it.nameRu,
                                    it.description
                                )
                            }?.let { successResult.invoke(it) }
                    }
                }
            })
    }

    override fun getFilmsFromRoom(listDB: (films: List<FilmItem>) -> Unit) {
        val task = Runnable {
        val result = App.instance.appDB?.let {
            it.getFilmDao().getAll()
        }
        result?.let {
            if(result.isNotEmpty()){
                result.map {
                    FilmItem(
                        it?.poster,
                        it!!.big_poster,
                        it!!.id,
                        it.title,
                        it.subtitle,
                        it.idDB,
                        it.isFavorite

                    )
                }.let { listDB.invoke(it) }
            }
            else{
                listDB.invoke(emptyList())
            }
        }
        }
        Executors.newSingleThreadExecutor().submit(task)
        }
}