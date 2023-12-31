package com.example.listadefilmes.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.listadefilmes.data.Film
import com.example.listadefilmes.data.FilmDatabase
import com.example.listadefilmes.repository.FilmRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FilmsViewModel(application: Application): AndroidViewModel(application) {
    private val repository: FilmRepository
    var allFilms : LiveData<List<Film>>
    lateinit var films : LiveData<Film>

    init {
        val dao = FilmDatabase.getDatabase(application).filmDAO()
        repository = FilmRepository(dao)
        allFilms = repository.getAllContacts()
    }

    fun insert(films: Film) = viewModelScope.launch(Dispatchers.IO){
        repository.insert(films)
    }

    fun update(films: Film) = viewModelScope.launch(Dispatchers.IO){
        repository.update(films)
    }

    fun delete(films: Film) = viewModelScope.launch(Dispatchers.IO){
        repository.delete(films)
    }

    fun getFilmById(id: Int) {
        viewModelScope.launch {
           films = repository.getFilmById(id)
        }
    }
}
