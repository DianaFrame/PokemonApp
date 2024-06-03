package com.example.data.api

import com.example.data.models.PokemonDetails
import com.example.data.models.PokemonImage
import com.example.data.models.PokemonResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonApi {
    @GET("pokemon")
    suspend fun getPokemons(
        @Query("limit") limit: Int,
        @Query("offset") offset: Int
    ): PokemonResponse

    @GET("pokemon/{name}")
    suspend fun getDetails(@Path("name") name: String): PokemonDetails

    @GET("pokemon/{name}")
    suspend fun getImage(@Path("name") name: String): PokemonImage

}