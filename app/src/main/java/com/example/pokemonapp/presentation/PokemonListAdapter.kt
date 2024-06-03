package com.example.pokemonapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.database.PokemonImageDb
import com.example.data.database.PokemonNameDb
import com.example.pokemonapp.R

class PokemonListAdapter(val listener: Listener) : RecyclerView.Adapter<PokemonListItemVh>() {
    private var dataset: MutableList<PokemonNameDb> = mutableListOf()
    private var pokemonImageDbs: MutableList<PokemonImageDb> = mutableListOf()
    fun setPokemons(pokemons: List<PokemonNameDb>, pokemonPokemonImageDbs: List<PokemonImageDb>) {
        dataset.clear()
        dataset.addAll(pokemons)
        pokemonImageDbs.clear()
        pokemonImageDbs.addAll(pokemonPokemonImageDbs)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonListItemVh {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.vh_pokemon, parent, false)
        return PokemonListItemVh(view)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: PokemonListItemVh, position: Int) {
        holder.bind(dataset[position], pokemonImageDbs[position], listener)
    }

}

