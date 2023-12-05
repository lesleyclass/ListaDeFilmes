package com.example.listadefilmes.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.listadefilmes.R
import com.example.listadefilmes.data.Film
import com.example.listadefilmes.databinding.FragmentFilmDetailsBinding
import com.example.listadefilmes.viewmodel.FilmsViewModel
import com.google.android.material.snackbar.Snackbar

class FilmDetailsFragment : Fragment() {
    private var _binding: FragmentFilmDetailsBinding? = null
    private val binding get() = _binding!!
    lateinit var film: Film
    private lateinit  var nameEditText: EditText
    private lateinit var releaseYearEditText: EditText
    private lateinit var isBeenWatchedEditText: EditText
    lateinit var viewModel: FilmsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(FilmsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFilmDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        nameEditText = binding.commonLayout.editTextNome
        releaseYearEditText = binding.commonLayout.editTextReleaseYear
        isBeenWatchedEditText = binding.commonLayout.editTextIsBeenWatched

        val idFilm = requireArguments().getInt("idFilm")

        viewModel.getFilmById(idFilm)

        viewModel.films.observe(viewLifecycleOwner) { result ->
            result?.let {
                film = result
                nameEditText.setText(film.name)
                releaseYearEditText.setText(film.releaseYear)
                isBeenWatchedEditText.setText(film.isBeenWatched.toString())
            }
        }

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.film_details_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionEditFilm -> {

                        film.name = nameEditText.text.toString()
                        film.releaseYear = releaseYearEditText.text.toString()
                        film.isBeenWatched = isBeenWatchedEditText.text.toString().isEmpty()

                        viewModel.update(film)

                        Snackbar.make(binding.root, "Filme alterado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }
                    R.id.actionDeleteFilm ->{
                        viewModel.delete(film)

                        Snackbar.make(binding.root, "Filme apagado", Snackbar.LENGTH_SHORT).show()

                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
