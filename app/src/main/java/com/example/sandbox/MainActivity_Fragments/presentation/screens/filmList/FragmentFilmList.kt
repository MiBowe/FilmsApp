package com.example.sandbox.MainActivity_Fragments.presentation.screens.filmList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.sandbox.MainActivity_Fragments.Injection
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.FilmItem
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.LDAdarpter
import com.example.sandbox.MainActivity_Fragments.presentation.Adapter.LoadStateAdapter
import com.example.sandbox.MainActivity_Fragments.presentation.screens.detailsFilm.DetailsFragment
import com.example.sandbox.R
import com.example.sandbox.databinding.FragmentFilmListBinding
import kotlinx.coroutines.launch

class FragmentFilmList : Fragment(), LDAdarpter.Listener {

    private val viewModel: FilmListViewModel by viewModels<FilmListViewModel>(
        factoryProducer = {Injection.provideViewModelFactory(owner = this)}
    )
    private var _binding: FragmentFilmListBinding? = null
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
        val films = viewModel.films
        val filmsAdapter = LDAdarpter(this)
        binding?.bindAdapter(filmsAdapter = filmsAdapter)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                films.collect{
                    filmsAdapter.submitData(it)
                }
            }
        }
    }
        override fun onClickFavorite(
            checkBox: CheckBox,
            item: FilmItem,
            position: Int,
        ) {
            checkBox.isChecked = item.isFavorite
            viewModel.onClickFavorite(checkBox,item,position)
        }

    override fun onClick(filmItem: FilmItem) {
        val bundle = Bundle()
        bundle.putInt("film", filmItem.id)
        parentFragmentManager
            .beginTransaction()
            .replace(R.id.frame_main, DetailsFragment.NewInstance(filmItem.id), "details_fragment")
            .addToBackStack("pop_stack")
            .commit()
    }

    private fun FragmentFilmListBinding.bindAdapter(filmsAdapter: LDAdarpter) {
        rvFilms.adapter = filmsAdapter.withLoadStateFooter(
            footer = LoadStateAdapter { filmsAdapter.retry() }
        )
        rvFilms.layoutManager = LinearLayoutManager(rvFilms.context)
        val decoration = DividerItemDecoration(rvFilms.context, DividerItemDecoration.VERTICAL)
        rvFilms.addItemDecoration(decoration)
    }
        override fun onDestroy() {
            super.onDestroy()
            _binding = null
        }
}


