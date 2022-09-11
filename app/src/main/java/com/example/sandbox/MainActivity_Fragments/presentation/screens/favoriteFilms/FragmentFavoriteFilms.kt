package com.example.sandbox.MainActivity_Fragments.presentation.screens.favoriteFilms

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.databinding.FragmentFavoriteFilmsBinding
import java.util.concurrent.Executors


class FragmentFavoriteFilms : Fragment(), Adapter.Listener {

    private val fav_films_VM: FavoriteFilmsViewModel by viewModels()
    var fav_films = mutableListOf<FilmItem>()
    private var _binding: FragmentFavoriteFilmsBinding? = null
    lateinit var adapter: Adapter
    protected val binding get() = _binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteFilmsBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val filmsObserve = Observer<List<FilmItem>>{
            fav_films.clear()
            fav_films.addAll(it)
            adapter.notifyDataSetChanged()
            Log.d("FragmentFF","$fav_films")
        }
        fav_films_VM.favoriteFilms.observe(viewLifecycleOwner, filmsObserve)
    }

    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        checkBox.isChecked = item.isFavorite
        checkBox.setOnClickListener {
            item.isFavorite = checkBox.isChecked
            Executors.newSingleThreadExecutor().execute(Runnable {
                App.instance.appDB?.let{
                    it.getFilmDao().updateFilm(item)
                }
            })
            adapter.deleteItem(item)
        }
    }

    private fun initRecycler() {
            adapter = Adapter(fav_films, this)
            binding?.rvFavFilms?.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


































/*class FragmentFavoriteFilms : Fragment(), Adapter.Listener {

    private var favorite_adapter: Adapter? = null
    private var _binding: FragmentFavoriteFilmsBinding? = null
    protected val binding get() = _binding
    lateinit var favoriteFilmsList : MutableList<KinopoiskFilmItem>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFavoriteFilmsBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecycler(){
        favoriteFilmsList = FilmListResponse.filmlist.filter { it.isFavorite } as MutableList<KinopoiskFilmItem>
        favorite_adapter = Adapter(favoriteFilmsList, this)
        binding?.rvFavFilms?.adapter = favorite_adapter
    }

    override fun onClickFavorite(checkBox: CheckBox, item: KinopoiskFilmItem, position: Int) {
        when {item.isFavorite -> checkBox.isChecked = true }
        checkBox.setOnClickListener(){
            item.isFavorite = false
            favorite_adapter?.delete(item)
            Log.d("FAVCLICK", "${item.isFavorite}, ${item.nameRu}")
            view.let {
                Snackbar.make(requireView(),"Фильм ${item.nameRu} удален из избранного", Snackbar.LENGTH_LONG )
                        //Сделал с максимальными костылями, простите заранее(( Я понимаю, что там другой нужен метод, но как реализовать - я не доганл(
                    .setAction("Верни плз(("){
                        favorite_adapter?.addFilm(item)
                        item.isFavorite = true
                        Toast.makeText(requireContext(), "Ладно,фильм ${item.nameRu} возвращен в избранное", Toast.LENGTH_LONG).show()
                    }.show()
            }
        }
    }

    override fun onClick(filmItem: KinopoiskFilmItem) {
        val bundle = Bundle()
        //bundle.putSerializable("film", filmItem)
        parentFragmentManager
            .beginTransaction()
            //.replace(R.id.frame_main, DetailsFragment.NewInstance(filmItem), "details_fragment")
            .addToBackStack("pop_stack")
            .commit()
    }
}*/