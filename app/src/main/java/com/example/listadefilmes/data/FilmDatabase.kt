package com.example.listadefilmes.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.listadefilmes.constants.Constants.FILM_LIST_DATABASE_NAME

@Database(entities = [Film::class], version = 1)
abstract class FilmDatabase: RoomDatabase() {
    abstract fun filmDAO(): FilmDAO

    companion object {
        @Volatile
        private var INSTANCE: FilmDatabase? = null

        fun getDatabase(context: Context): FilmDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FilmDatabase::class.java,
                    FILM_LIST_DATABASE_NAME,
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
