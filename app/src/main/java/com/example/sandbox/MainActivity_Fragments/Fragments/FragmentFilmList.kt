package com.example.sandbox.MainActivity_Fragments.Fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import com.example.sandbox.MainActivity_Fragments.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.Adapter.FilmList
import com.example.sandbox.R
import com.example.sandbox.databinding.FragmentFilmListBinding
import com.google.android.material.snackbar.Snackbar

class FragmentFilmList : Fragment(), Adapter.Listener {

    private var adapter : Adapter? = null
    private var _binding : FragmentFilmListBinding? = null
    private val binding get() = _binding

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return FragmentFilmListBinding.inflate(inflater).also {
            _binding = it
        }.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun initRecycler() {
       val filmList = FilmList.filmlist as MutableList<FilmItem>
        adapter = Adapter(filmList, this)
        binding?.rvFilms?.adapter = adapter
     }

    override fun onClickFavorite(checkBox: CheckBox, item: FilmItem, position: Int) {
        when {item.isFavorite -> checkBox.isChecked = true }
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            FilmList.filmlist[position].isFavorite = isChecked
            view?.let {

                when(item.isFavorite){
                    true -> {
                        Snackbar.make(requireView(), "Фильм ${item.title} добавлен в избранное", Snackbar.LENGTH_LONG)
                            .show()
                    }
                    false ->{
                        Snackbar.make(requireView(),"Фильм ${item.title} удален из избранного", Snackbar.LENGTH_LONG )
                            .show()
                    }
                }
            }
        }
    }



    override fun onClick(filmItem: FilmItem) {
        val bundle = Bundle()
        bundle.putSerializable("film", filmItem)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, DetailsFragment.NewInstance(filmItem), "details_fragment")
            .addToBackStack(null)
            .commit()
    }
}