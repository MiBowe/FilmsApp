package com.example.sandbox.MainActivity_Fragments.data.repository.films

import androidx.paging.PagingSource
import com.example.sandbox.MainActivity_Fragments.data.paging.FilmsPagingSource
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem

interface FilmsRepository {

    fun getFavoriteFilms(films: (filmList: List<FilmItem>) -> Unit)

    fun getFilmsFromRoom(page: Int, listDB: (films: List<FilmItem>) -> Unit)

    fun getFilms(page: Int, successResult: (films: List<FilmItem>) -> Unit)

    fun getMovieDetails(id: Int, successResult: (film :FilmItem?) -> Unit)

}