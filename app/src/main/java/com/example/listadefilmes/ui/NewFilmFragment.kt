package com.example.listadefilmes.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.listadefilmes.R
import com.example.listadefilmes.data.Film
import com.example.listadefilmes.databinding.FragmentNewFilmBinding
import com.example.listadefilmes.viewmodel.FilmsViewModel
import com.google.android.material.snackbar.Snackbar

class NewFilmFragment : Fragment() {
    private var _binding: FragmentNewFilmBinding? = null
    private val binding get() = _binding!!
    lateinit var viewModel: FilmsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[FilmsViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNewFilmBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.new_film_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.actionSaveFilm -> {
                        val name = binding.commonLayout.editTextNome.text.toString()
                        val releaseYear = binding.commonLayout.editTextReleaseYear.text.toString()
                        val isBeenWatched = binding.commonLayout.radioButtonYes.isChecked
                        val film = Film(0, name, releaseYear, isBeenWatched)

                        viewModel.insert(film)

                        Snackbar.make(binding.root, "Filme inserido", Snackbar.LENGTH_SHORT).show()
                        findNavController().popBackStack()
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
