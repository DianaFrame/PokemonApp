package com.example.pokemonapp.presentation

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.data.database.PokemonImageDb
import com.example.data.database.PokemonNameDb
import com.example.pokemonapp.R

class PokemonListItemVh(view: View) : RecyclerView.ViewHolder(view) {
    private val pokeImage: ImageView = view.findViewById(R.id.imVPhoto)
    private val pokeName: TextView = view.findViewById(R.id.tVName)
    fun bind(
        pokemonNameDb: PokemonNameDb,
        pokemonPokemonImageDb: PokemonImageDb,
        listener: Listener
    ) {
        Glide.with(pokeImage).load(pokemonPokemonImageDb.imageURL).into(pokeImage)
        pokeName.text = "${pokemonNameDb.name}"
        itemView.setOnClickListener {
            listener.onClick(pokemonNameDb)
        }
    }
}