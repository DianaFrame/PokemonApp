package com.example.pokemonapp.presentation

import com.example.data.database.PokemonNameDb


interface Listener {
    fun onClick(pokemonNameDb: PokemonNameDb)
}