package com.example.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface Dao {
    @Insert
    suspend fun insertPokemonName(pokemonList: PokemonNameDb)

    @Insert
    suspend fun insertPokemonImage(pokemonPokemonImageListDb: PokemonImageDb)

    @Insert
    suspend fun insertDetails(pokemonDetails: PokemonDetailsDb)

    @Query("SELECT * FROM pokemonName")
    suspend fun getAllPokemonName(): List<PokemonNameDb>

    @Query("SELECT * FROM pokemonImage")
    suspend fun getAllPokemonImage(): List<PokemonImageDb>

    @Query("SELECT * FROM pokemonDetails WHERE name = :name")
    suspend fun getDetailsByName(name: String): PokemonDetailsDb?
}