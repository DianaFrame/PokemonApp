package com.example.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


data class PokemonDetails(
    val id: Int? = null,
    val name: String,
    val sprites: PokemonSprites,
    val base_experience: Int,
    val height: Int,
    val order: Int,
    val weight: Int,
)