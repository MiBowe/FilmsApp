package com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem


class DetailsFilmViewModel: ViewModel() {

    private val _filmDetails = MutableLiveData<FilmItem>()
    val filmDetails: LiveData<FilmItem> = _filmDetails


    fun getFilmByID(filmId: Int){
            var film = App.instance.appDB!!.getFilmDao().getFilmByID(filmId)
        _filmDetails.postValue(film)
    }


}