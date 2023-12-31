package com.example.listadefilmes.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.listadefilmes.data.Film

@Dao
interface FilmDAO {
    @Insert
    suspend fun insert(film: Film)

    @Update
    suspend fun update (film: Film)

    @Delete
    suspend fun delete(film: Film)

    @Query("SELECT * FROM film ORDER BY name")
    fun getAllFilms(): LiveData<List<Film>>

    @Query("SELECT * FROM film WHERE id=:id")
    fun getFilmById(id: Int): LiveData<Film>
}
