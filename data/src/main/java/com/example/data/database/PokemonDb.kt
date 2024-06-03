package com.example.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [PokemonNameDb::class, PokemonDetailsDb::class, PokemonImageDb::class],
    version = 1
)
abstract class PokemonDb : RoomDatabase() {
    abstract fun getDao(): Dao

    companion object {
        fun getDb(context: Context): PokemonDb {
            return Room.databaseBuilder(
                context.applicationContext,
                PokemonDb::class.java,
                "pokemon.db"
            ).build()
        }
    }
}