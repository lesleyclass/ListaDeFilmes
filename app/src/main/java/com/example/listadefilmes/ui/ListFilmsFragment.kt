package com.example.listadefilmes.ui


import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listadefilmes.adapter.FilmAdapter
import com.example.listadefilmes.R
import com.example.listadefilmes.databinding.FragmentListFilmsBinding
import com.example.listadefilmes.viewmodel.FilmsViewModel

class ListFilmsFragment : Fragment(){

    private var _binding: FragmentListFilmsBinding? = null
    private val binding get() = _binding!!
    lateinit var filmAdapter: FilmAdapter
    private lateinit var viewModel: FilmsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentListFilmsBinding.inflate(inflater, container, false)
        binding.floatingActionButton.setOnClickListener { findNavController().navigate(R.id.actionListFilmsToNewFilmFragment) }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        configureRecyclerView()

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.list_films_menu, menu)

                val searchView = menu.findItem(R.id.actionSearch).actionView as SearchView
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        TODO("Not yet implemented")
                    }

                    override fun onQueryTextChange(p0: String?): Boolean {
                        filmAdapter.filter.filter(p0)
                        return true
                    }
                })
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                TODO("Not yet implemented")
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    private fun configureRecyclerView() {
        viewModel = ViewModelProvider(this)[FilmsViewModel::class.java]

        viewModel.allFilms.observe(viewLifecycleOwner) { list ->
            list?.let {
                filmAdapter.updateList(list)
            }
        }

        val recyclerView = binding.recyclerview
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        filmAdapter = FilmAdapter()
        recyclerView.adapter = filmAdapter

        val listener = object : FilmAdapter.FilmListener {
            override fun onItemClick(pos: Int) {
                val c = filmAdapter.filmsListFilterable[pos]

                val bundle = Bundle()
                bundle.putInt("idFilm", c.id)

                findNavController().navigate(
                    R.id.actionListFilmsFragmentToListDetailsFragment,
                    bundle
                )
            }
        }
        filmAdapter.setClickListener(listener)
    }
}
