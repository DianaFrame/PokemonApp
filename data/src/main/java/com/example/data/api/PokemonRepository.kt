package com.example.data.api

//import com.example.data.database.PokemonDb
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
    private suspend fun getPokemonsFromAPI() {
        val pokemonName = api.getPokemons(
            limit = 30,
            offset = 0
        ).results

        if (db.getDao().getAllPokemonName().isEmpty()) {
            for (pokemon in pokemonName) {
                db.getDao().insertPokemonName(PokemonNameDb(name = pokemon.name))
            }
        }
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

    private suspend fun getImageFromAPI(pokemons: List<PokemonNameDb>) {
        val pokemonImageList = mutableListOf<PokemonImage>()
        for (pokemon in pokemons) {
            pokemonImageList.add(api.getImage(name = pokemon.name))
        }
        if (db.getDao().getAllPokemonImage().isEmpty()) {
            for (image in pokemonImageList) {
                db.getDao()
                    .insertPokemonImage(PokemonImageDb(imageURL = image.sprites.front_default))
            }
        }
    }

    suspend fun getPokemonNamesFromDb(): List<PokemonNameDb> {
        getPokemonsFromAPI()
        return db.getDao().getAllPokemonName()
    }

    suspend fun getDetailsFromDb(name: String): PokemonDetailsDb {
        val pokemonDetails =
            db.getDao().getDetailsByName(name = name) ?: return getDetailsFromAPI(name)
        return pokemonDetails
    }

    suspend fun getPokemonImageFromDb(pokemons: List<PokemonNameDb>): List<PokemonImageDb> {
        getImageFromAPI(pokemons)
        return db.getDao().getAllPokemonImage()
    }
}