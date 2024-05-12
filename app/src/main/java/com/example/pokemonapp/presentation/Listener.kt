package com.example.pokemonapp.presentation

import com.example.data.models.Pokemon


interface Listener {
    fun onClick(pokemon: Pokemon)
}