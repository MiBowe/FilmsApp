package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.databinding.FragmentFilmListBinding

class FragmentFilmList : Fragment(), Adapter.Listener {

    private val viewModel: FilmListViewModel by viewModels()
    private var _binding: FragmentFilmListBinding? = null
    lateinit var adapter: Adapter
    protected val binding get() = _binding
    var newFilmList = mutableListOf<FilmItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val filmsObserv = Observer<MutableList<FilmItem>>{
            newFilmList = it
        }
        viewModel.filmList.observe(viewLifecycleOwner,filmsObserv)
        _binding = FragmentFilmListBinding.inflate(inflater)
        return _binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()

    }

    private fun initRecycler() {
        adapter = Adapter(newFilmList,this)
        binding?.rvFilms?.adapter = adapter
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}


