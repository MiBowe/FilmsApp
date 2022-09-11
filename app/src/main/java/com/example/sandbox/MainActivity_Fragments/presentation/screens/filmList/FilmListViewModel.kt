package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList


import android.os.Bundle
import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmsRepositoryImpl
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import java.util.concurrent.Executors

open class FilmListViewModel(): ViewModel(), Adapter.Listener {
    val filmList = MutableLiveData<List<FilmItem>>()

    init {
        Log.d("FilmsVXM", this.toString())
        getFilmList()

    }

    private fun getFilmList() {
            FilmsRepositoryImpl.getFilmsFromRoom{films ->
                films.let {
                    if (films.isNotEmpty()){
                        filmList.postValue(films)
                        Log.d("FILMS","Films from DB")
                        Log.d("FILMS_DB","$films")
                        Log.d("FILMS_DB","${filmList.postValue(films)}")
                    }
                    else {
                        FilmsRepositoryImpl.getFilms { films -> filmList.postValue(films) }
                        Log.d("FILMS","Films from retrofit")
                        Log.d("FILMS_retro","$films")
                        Log.d("FILMS_retro","${filmList.postValue(films)}")
                    }
                }
            }
    }


    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        item.isFavorite = checkBox.isChecked
        Executors.newSingleThreadExecutor().execute(Runnable {
            App.instance.appDB?.let{
                it.getFilmDao().updateFilm(item)
            }
            Log.d("test favorite", "onClickFavorite: клацнули")
        })

        }

    override fun onClick(filmItem: FilmItem) {
    }
}
