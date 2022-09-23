package com.example.sandbox.MainActivity_Fragments


import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmRepository
import com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList.VMFactory

object Injection {
    private fun provideArticleRepository(): FilmRepository = FilmRepository()

    fun provideViewModelFactory(owner: SavedStateRegistryOwner): ViewModelProvider.Factory {
        return VMFactory(owner, provideArticleRepository())
    }
}