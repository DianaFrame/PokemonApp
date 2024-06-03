package com.example.pokemonapp.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleOwner
import com.bumptech.glide.Glide
import com.example.data.api.PokemonRepository
import com.example.pokemonapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailsFragment : Fragment() {
    private val viewModel: PokemonViewModel by activityViewModels()
    private var image: ImageView? = null
    private var name: TextView? = null
    private var basedExperience: TextView? = null
    private var height: TextView? = null
    private var weight: TextView? = null
    private var order: TextView? = null
    private var buttonBack: Button? = null
    private var progressBar: ProgressBar? = null
    private var cardDetails: CardView? = null
    private var choosePokemonName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_pokemon_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.pokemon.observe(activity as LifecycleOwner) { pokemon ->
            choosePokemonName = pokemon.name
        }
        val pokemonRepository = PokemonRepository(view.context)
        image = view.findViewById(R.id.image)
        name = view.findViewById(R.id.name)
        basedExperience = view.findViewById(R.id.basedExperience)
        height = view.findViewById(R.id.height)
        weight = view.findViewById(R.id.weight)
        order = view.findViewById(R.id.order)
        buttonBack = view.findViewById(R.id.bBack)
        progressBar = view.findViewById(R.id.prBarDetails)
        cardDetails = view.findViewById(R.id.cardView)

        buttonBack?.setOnClickListener {
            (activity as AppCompatActivity).supportFragmentManager.beginTransaction()
                .replace(R.id.main, PokemonListFragment.newInstance()).commit()
        }
        progressBar?.visibility = View.VISIBLE
        cardDetails?.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonDetails = pokemonRepository.getDetailsFromDb(choosePokemonName!!)
            (activity as AppCompatActivity).runOnUiThread {
                progressBar?.visibility = View.GONE
                cardDetails?.visibility = View.VISIBLE
                Glide.with(image!!).load(pokemonDetails.imageURL).into(image!!)
                name?.text = "${resources.getText(R.string.name)}: $choosePokemonName"
                basedExperience?.text =
                    "${resources.getText(R.string.based_experience)}: ${pokemonDetails.base_experience}"
                height?.text = "${resources.getText(R.string.height)}: ${pokemonDetails.height}"
                weight?.text = "${resources.getText(R.string.weight)}: ${pokemonDetails.weight}"
                order?.text = "${resources.getText(R.string.order)}: ${pokemonDetails.order}"
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() = PokemonDetailsFragment()
    }
}