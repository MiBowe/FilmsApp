package com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmsRepositoryImpl
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.KinopoiskFilmItem
import java.util.concurrent.Executors


class DetailsFilmViewModel: ViewModel() {

    private val _filmDetails = MutableLiveData<FilmItem?>()
    val filmDetails: LiveData<FilmItem?> = _filmDetails

    fun getFilmByID(filmId: Int){
        FilmsRepositoryImpl.getMovieDetails(filmId){
            var filmDtls:FilmItem? = it
            _filmDetails.postValue(filmDtls)
        }
    }


}