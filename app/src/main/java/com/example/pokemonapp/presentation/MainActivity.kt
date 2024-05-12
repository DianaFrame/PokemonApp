package com.example.pokemonapp.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.PokemonRepository
import com.example.data.models.Pokemon
import com.example.data.models.PokemonDetails
import com.example.pokemonapp.Constants
import com.example.pokemonapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), Listener {
    private val adapter: PokemonListAdapter? by lazy { PokemonListAdapter(this) }
    private var recycler: RecyclerView? = null
    private val pokemonRepository = PokemonRepository()
    private var progressBar: ProgressBar? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        recycler = findViewById(R.id.rVPokemonList)
        recycler?.adapter = adapter
        recycler?.layoutManager = GridLayoutManager(this, 3)
        progressBar = findViewById(R.id.prBarPokeList)
        recycler?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val listPokemons = pokemonRepository.getPokemons()
            val listImages = mutableListOf<PokemonDetails>()
            for (pokemon in listPokemons) {
                listImages.add(pokemonRepository.getDetails(name = pokemon.name))
            }
            runOnUiThread {
                recycler?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
                adapter?.setPokemons(listPokemons, listImages)
            }
        }

    }

    override fun onClick(pokemon: Pokemon) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra(Constants.POKEMON_NAME, pokemon.name)
        startActivity(intent)
    }
}