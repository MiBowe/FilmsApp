package com.example.sandbox.MainActivity_Fragments.presentation.screens.favoriteFilms

import android.util.Log
import android.widget.CheckBox
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmsRepositoryImpl
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.LDAdarpter
import java.util.concurrent.Executors

open class FavoriteFilmsViewModel: ViewModel(), LDAdarpter.Listener {

    val _favoriteFilms = MutableLiveData<List<FilmItem>>()
    val favoriteFilms: LiveData<List<FilmItem>> = _favoriteFilms

    init {

        Log.d("VM favorite frag", ": init ")
        getFavoriteList()

    }

    private fun getFavoriteList() {
        FilmsRepositoryImpl.getFavoriteFilms{ films ->
            var newList = films.filter { it.isFavorite }
            Log.d("films_db_fav", "getFavoriteList: ${newList} ")

            try {
                _favoriteFilms.postValue(newList)
                Log.d("PostValue", "getFavoriteList: ${_favoriteFilms.postValue(newList)}")
            }
            catch (e: Exception){
                Log.d("FAILURE", "${e.message}")
            }
        }
    }

    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        item.isFavorite = checkBox.isChecked
        Executors.newSingleThreadExecutor().execute(Runnable {
            App.instance.appDB?.let{
                it.getFilmDao().updateFilm(item)
            }
        })
    }
}