package com.example.pokemonapp.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetails
import com.example.pokemonapp.R

class PokemonListAdapter(val listener: Listener) : RecyclerView.Adapter<PokemonListItemVh>() {
    private var dataset: MutableList<Pokemon> = mutableListOf()
    private var images: MutableList<PokemonDetails> = mutableListOf()
    fun setPokemons(pokemons: List<Pokemon>, pokemonImages: List<PokemonDetails>) {
        dataset.clear()
        dataset.addAll(pokemons)
        images.clear()
        images.addAll(pokemonImages)
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
        holder.bind(dataset[position], images[position], listener)
    }

}