package com.example.pokemonapp.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.data.database.PokemonNameDb

class PokemonViewModel : ViewModel() {
    val pokemon: MutableLiveData<PokemonNameDb> by lazy {
        MutableLiveData()
    }
}