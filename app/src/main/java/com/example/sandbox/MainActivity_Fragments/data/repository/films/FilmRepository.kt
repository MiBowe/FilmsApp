package com.example.sandbox.MainActivity_Fragments.data.repository.films

import com.example.sandbox.MainActivity_Fragments.data.paging.FilmsPagingSource

class FilmRepository {

    fun filmsPagingSource() = FilmsPagingSource(20)

}