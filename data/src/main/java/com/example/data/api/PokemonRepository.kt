package com.example.data.api

import com.example.data.Retrofit
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetails

class PokemonRepository {
    private val api: PokemonApi by lazy { Retrofit.getClient().create(PokemonApi::class.java) }

    suspend fun getPokemons(): List<Pokemon> {
        return api.getPokemons(
            limit = 30,
            offset = 0
        ).results
    }

    suspend fun getDetails(name: String): PokemonDetails {
        return api.getDetails(name)
    }
}