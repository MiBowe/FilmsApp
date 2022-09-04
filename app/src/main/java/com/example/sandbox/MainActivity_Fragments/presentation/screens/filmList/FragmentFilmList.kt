package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.sandbox.MainActivity_Fragments.App
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.Adapter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm.DetailsFragment
import com.example.sandbox.R
import com.example.sandbox.databinding.FragmentFilmListBinding
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class FragmentFilmList : Fragment(), Adapter.Listener {

    private val viewModel: FilmListViewModel by viewModels()
    private var _binding: FragmentFilmListBinding? = null
    lateinit var adapter: Adapter
    protected val binding get() = _binding
    val newFilmList = mutableListOf<FilmItem>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        _binding = FragmentFilmListBinding.inflate(inflater)
        return _binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
        val filmsObserve = Observer<List<FilmItem>> {
            newFilmList.clear()
            newFilmList.addAll(it)
            adapter.notifyDataSetChanged()
            Log.d("FRFL", "$newFilmList: ")
        }
        viewModel.filmList.observe(viewLifecycleOwner, filmsObserve)

        val task = Runnable {
            App.instance.appDB?.let {
                it.getFilmDao().insertList(newFilmList)
            }
        }
        Executors.newSingleThreadScheduledExecutor().schedule(task,2000,TimeUnit.MILLISECONDS)
    }
        override fun onClickFavorite(
            checkBox: CheckBox,
            item: FilmItem,
            position: Int,
        ) {
            checkBox.isChecked = item.isFavorite
            checkBox.setOnClickListener() {
                viewModel.onClickFavorite(checkBox, item, position)
            }

        }

    override fun onClick(filmItem: FilmItem) {
        val bundle = Bundle()
        bundle.putSerializable("film", filmItem.id)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, DetailsFragment.NewInstance(filmItem), "details_fragment")
            .addToBackStack("pop_stack")
            .commit()
    }

        private fun initRecycler() {
            adapter = Adapter(newFilmList, this)
            binding?.rvFilms?.adapter = adapter
        }


        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
    }


