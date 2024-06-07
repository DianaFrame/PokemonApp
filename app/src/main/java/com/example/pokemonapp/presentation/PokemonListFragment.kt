package com.example.pokemonapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.data.api.PokemonRepository
import com.example.data.database.PokemonNameDb
import com.example.pokemonapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonListFragment : Fragment(), Listener {
    private val viewModel: PokemonViewModel by activityViewModels()
    private val adapter: PokemonListAdapter? by lazy { PokemonListAdapter(this) }
    private var recycler: RecyclerView? = null
    private var progressBar: ProgressBar? = null
    

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val pokemonRepository = PokemonRepository(view.context)
        recycler = view.findViewById(R.id.rVPokemonList)
        recycler?.adapter = adapter
        recycler?.layoutManager = GridLayoutManager(view.context, 3)
        progressBar = view.findViewById(R.id.prBarPokeList)
        recycler?.visibility = View.GONE
        progressBar?.visibility = View.VISIBLE
        CoroutineScope(Dispatchers.IO).launch {
            val listPokemons = pokemonRepository.getPokemonNamesFromDb()
            val listImages = pokemonRepository.getPokemonImageFromDb(listPokemons)
            (activity as AppCompatActivity).runOnUiThread {
                recycler?.visibility = View.VISIBLE
                progressBar?.visibility = View.GONE
                adapter?.setPokemons(listPokemons, listImages)
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PokemonListFragment()
    }

    override fun onClick(pokemonNameDb: PokemonNameDb) {
        viewModel.pokemon.value = pokemonNameDb
        (activity as AppCompatActivity)
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.main, PokemonDetailsFragment.newInstance())
            .commit()
    }
}