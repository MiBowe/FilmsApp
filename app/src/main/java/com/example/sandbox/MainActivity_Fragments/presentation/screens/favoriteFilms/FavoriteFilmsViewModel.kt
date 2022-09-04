package com.example.sandbox.MainActivity_Fragments.presentation.screens.favoriteFilms

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmsRepositoryImpl
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import kotlinx.coroutines.launch

open class FavoriteFilmsViewModel: ViewModel(), Adapter.Listener {

    var favoriteFilms = MutableLiveData<List<FilmItem>>()

    init {

        Log.d("VM favorite frag", ": init ")
        getFavoriteList()

    }

    private fun getFavoriteList() {
        FilmsRepositoryImpl.getFilmsFromRoom { films ->
            var newList = films.filter { it.isFavorite  }
            Log.d("films_db_fav", "getFavoriteList: ${newList} ")

            try {
                favoriteFilms.postValue(newList)
                Log.d("PostValue", "getFavoriteList: ${favoriteFilms.postValue(newList)}")
            }
            catch (e: Exception){
                Log.d("FAILURE", "${e.message}")
            }
        }
    }

}