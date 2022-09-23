package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import android.util.Log
import android.widget.CheckBox
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.LDAdarpter
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmRepository
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import kotlinx.coroutines.flow.Flow
import java.util.concurrent.Executors


class FilmListViewModel(repository: FilmRepository): ViewModel(), LDAdarpter.Listener {

    val films: Flow<PagingData<FilmItem>> = Pager(
        config = PagingConfig(
            pageSize = 20,
            maxSize = 60,
            enablePlaceholders = false,
            initialLoadSize = 1,
            prefetchDistance = 1
        ),
        pagingSourceFactory = { repository.filmsPagingSource() }
    )
        .flow
        .cachedIn(viewModelScope)

    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        checkBox.setOnClickListener() {
            item.isFavorite = checkBox.isChecked
            Executors.newSingleThreadExecutor().execute(Runnable {
                App.instance.appDB?.let{
                    it.getFilmDao().updateFilm(item)
                }
                Log.d("test favorite", "onClickFavorite: клацнули")
            })
        }
    }
}