package com.example.data.api

import android.content.Context
import com.example.data.Retrofit
import com.example.data.database.PokemonDb
import com.example.data.database.PokemonDetailsDb
import com.example.data.database.PokemonImageDb
import com.example.data.database.PokemonNameDb
import com.example.data.models.PokemonImage

class PokemonRepository(context: Context) {
    private val api: PokemonApi by lazy { Retrofit.getClient().create(PokemonApi::class.java) }
    private val db = PokemonDb.getDb(context)
    private suspend fun getPokemonsFromAPI(): List<PokemonNameDb> {
        val pokemonNames = api.getPokemons(
            limit = 30,
            offset = 0
        ).results
        val pokemonNamesDb = mutableListOf<PokemonNameDb>()
        for (pokemon in pokemonNames) {
            pokemonNamesDb.add(PokemonNameDb(name = pokemon.name))
            db.getDao().insertPokemonName(PokemonNameDb(name = pokemon.name))
        }
        return pokemonNamesDb
    }

    private suspend fun getDetailsFromAPI(name: String): PokemonDetailsDb {
        val pokemonDetails = api.getDetails(name)
        val pokemonDetailsDb = PokemonDetailsDb(
            name = pokemonDetails.name,
            imageURL = pokemonDetails.sprites.front_default,
            base_experience = pokemonDetails.base_experience,
            height = pokemonDetails.height,
            weight = pokemonDetails.weight,
            order = pokemonDetails.order
        )
        db.getDao().insertDetails(pokemonDetailsDb)

        return pokemonDetailsDb
    }

    private suspend fun getImageFromAPI(pokemons: List<PokemonNameDb>): List<PokemonImageDb> {
        val pokemonImageList = mutableListOf<PokemonImage>()
        for (pokemon in pokemons) {
            pokemonImageList.add(api.getImage(name = pokemon.name))
        }
        val pokemonImagesDb = mutableListOf<PokemonImageDb>()
        for (image in pokemonImageList) {
            pokemonImagesDb.add(PokemonImageDb(imageURL = image.sprites.front_default))
            db.getDao()
                .insertPokemonImage(PokemonImageDb(imageURL = image.sprites.front_default))
        }
        return pokemonImagesDb
    }

    suspend fun getPokemonNamesFromDb(): List<PokemonNameDb> {
        val pokemonNames = db.getDao().getAllPokemonName()
        if (pokemonNames.isEmpty()) return getPokemonsFromAPI()
        return pokemonNames
    }

    suspend fun getDetailsFromDb(name: String): PokemonDetailsDb {
        val pokemonDetails =
            db.getDao().getDetailsByName(name = name) ?: return getDetailsFromAPI(name = name)
        return pokemonDetails
    }

    suspend fun getPokemonImageFromDb(pokemons: List<PokemonNameDb>): List<PokemonImageDb> {
        val pokemonImages =
            db.getDao().getAllPokemonImage()
        if (pokemonImages.isEmpty()) return getImageFromAPI(pokemons = pokemons)
        return pokemonImages
    }
}