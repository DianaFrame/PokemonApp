package com.example.pokemonapp.presentation


import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.bumptech.glide.Glide
import com.example.data.api.PokemonRepository
import com.example.pokemonapp.Constants
import com.example.pokemonapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PokemonDetailsActivity : AppCompatActivity() {
    private val pokemonRepository = PokemonRepository()
    private var image: ImageView? = null
    private var name: TextView? = null
    private var basedExperience: TextView? = null
    private var height: TextView? = null
    private var weight: TextView? = null
    private var order: TextView? = null
    private var buttonBack: Button? = null
    private var progressBar: ProgressBar? = null
    private var cardDetails: CardView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_pokemon_details)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val choosePokemonName: String = intent.getStringExtra(Constants.POKEMON_NAME) ?: ""
        image = findViewById(R.id.image)
        name = findViewById(R.id.name)
        basedExperience = findViewById(R.id.basedExperience)
        height = findViewById(R.id.height)
        weight = findViewById(R.id.weight)
        order = findViewById(R.id.order)
        buttonBack = findViewById(R.id.bBack)
        progressBar = findViewById(R.id.prBarDetails)
        cardDetails = findViewById(R.id.cardView)

        buttonBack?.setOnClickListener {
            finish()
        }
        progressBar?.visibility = View.VISIBLE
        cardDetails?.visibility = View.GONE
        CoroutineScope(Dispatchers.IO).launch {
            val pokemonDetails = pokemonRepository.getDetails(choosePokemonName)
            runOnUiThread {
                progressBar?.visibility = View.GONE
                cardDetails?.visibility = View.VISIBLE
                Glide.with(image!!).load(pokemonDetails.sprites.front_default).into(image!!)
                name?.text = "${resources.getText(R.string.name)}: $choosePokemonName"
                basedExperience?.text = "${resources.getText(R.string.based_experience)}: ${pokemonDetails.base_experience}"
                height?.text = "${resources.getText(R.string.height)}: ${pokemonDetails.height}"
                weight?.text = "${resources.getText(R.string.weight)}: ${pokemonDetails.weight}"
                order?.text = "${resources.getText(R.string.order)}: ${pokemonDetails.order}"
            }
        }


    }
}