package com.example.sandbox.MainActivity_Fragments.data.paging

import android.util.Log
import android.util.Size
import android.widget.Toast
import androidx.paging.Pager
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.sandbox.MainActivity_Fragments.data.repository.films.FilmsRepositoryImpl
import com.example.sandbox.MainActivity_Fragments.data.room.Dao
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import retrofit2.HttpException
import java.io.IOException
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

class FilmsPagingSource(
    private val loadSize : Int
) : PagingSource<Int, FilmItem>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, FilmItem> {
        val page = params.key ?: 1
        val films = mutableListOf<FilmItem>()

        suspendCoroutine<List<FilmItem>> { continuation ->
            FilmsRepositoryImpl.getFilmsFromRoom(page){ it ->
                if (it.size == 20){
                    Log.d("OOPS!","Films from DB ${it.size}, $page")
                    films.addAll(it)
                } else {
                    FilmsRepositoryImpl.getFilms(page){
                        Log.d("OOPS!","Films from DB ${it.size}, $page")
                        films.addAll(it)
                    }
                }
                continuation.resume(it)
            }
        }
        return try {
            LoadResult.Page(
            data = films,
            prevKey = if (page == 1) null else page - 1,
            nextKey = if(page<5 || films.size==20){ page + 1 }
            else
            {null}
            )
    }
        catch (e:IOException){
            LoadResult.Error(e)
        } catch (e: HttpException){
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, FilmItem>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
    }
}