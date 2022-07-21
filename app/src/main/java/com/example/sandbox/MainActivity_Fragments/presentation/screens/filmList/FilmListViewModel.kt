package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmList

class FilmListViewModel: ViewModel(){

    private val _films = MutableLiveData<MutableList<FilmList>>()
    var films: LiveData<MutableList<FilmList>> = _films

    fun getFilms(){

        films = _films

    }

}