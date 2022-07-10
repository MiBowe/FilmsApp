package com.example.sandbox.MainActivity_Fragments.Fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import com.example.sandbox.MainActivity_Fragments.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.Adapter.FilmList
import com.example.sandbox.R
import com.example.sandbox.databinding.FragmentFavoriteFilmsBinding
import com.google.android.material.snackbar.Snackbar

class FragmentFavoriteFilms : Fragment(), Adapter.Listener {

    private var favorite_adapter: Adapter? = null
    private var _binding: FragmentFavoriteFilmsBinding? = null
    protected val binding get() = _binding
    lateinit var favoriteFilmsList : MutableList<FilmItem>

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
        favoriteFilmsList = FilmList.filmlist.filter { it.isFavorite } as MutableList<FilmItem>
        favorite_adapter = Adapter(favoriteFilmsList, this)
        binding?.rvFavFilms?.adapter = favorite_adapter
    }

    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        when {item.isFavorite -> checkBox.isChecked = true }
        checkBox.setOnClickListener(){
            item.isFavorite = false
            favorite_adapter?.delete(item)
            Log.d("FAVCLICK", "${item.isFavorite}, ${item.title}")
            view.let {
                Snackbar.make(requireView(),"Фильм ${item.title} удален из избранного", Snackbar.LENGTH_LONG )
                        //Сделал с максимальными костылями, простите заранее(( Я понимаю, что там другой нужен метод, но как реализовать - я не доганл(
                    .setAction("Верни плз(("){
                        favorite_adapter?.addFilm(item)
                        item.isFavorite = true
                        Toast.makeText(requireContext(), "Ладно,фильм ${item.title} возвращен в избранное", Toast.LENGTH_LONG).show()
                    }.show()
            }
        }
    }

    override fun onClick(filmItem: FilmItem) {
        val bundle = Bundle()
        bundle.putSerializable("film", filmItem)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, DetailsFragment.NewInstance(filmItem), "details_fragment")
            .addToBackStack("pop_stack")
            .commit()
    }
}