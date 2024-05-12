package com.example.pokemonapp.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetails
import com.example.pokemonapp.R

class PokemonListItemVh(view: View) : RecyclerView.ViewHolder(view) {
    private val pokeImage: ImageView = view.findViewById(R.id.imVPhoto)
    private val pokeName: TextView = view.findViewById(R.id.tVName)
    fun bind(pokemon: Pokemon, pokemonDetails: PokemonDetails, listener: Listener) {
        Glide.with(pokeImage).load(pokemonDetails.sprites.front_default).into(pokeImage)
        pokeName.text = "${pokemon.name}"
        itemView.setOnClickListener {
            listener.onClick(pokemon)
        }
    }
}