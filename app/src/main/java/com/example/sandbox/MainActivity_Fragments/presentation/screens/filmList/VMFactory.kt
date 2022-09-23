package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmRepository

class VMFactory(
    owner: SavedStateRegistryOwner,
    private val repository: FilmRepository,
    ) : AbstractSavedStateViewModelFactory(owner, null) {

        override fun <T : ViewModel?> create(
            key: String,
            modelClass: Class<T>,
            handle: SavedStateHandle
        ): T {
            if (modelClass.isAssignableFrom(FilmListViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return FilmListViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }