package com.example.sandbox.MainActivity_Fragments.data.repository.films

import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem

interface FilmsRepository {

    fun getFilms(successResult: (films:List<FilmItem>) -> Unit)

    fun getFilmsFromRoom(listDB: (films:List<FilmItem>) -> Unit)


}