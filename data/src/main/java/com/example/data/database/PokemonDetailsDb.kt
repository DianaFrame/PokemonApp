package com.example.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pokemonDetails")
data class PokemonDetailsDb(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "imageURL")
    var imageURL: String,
    @ColumnInfo(name = "baseExperience")
    var base_experience: Int,
    @ColumnInfo(name = "height")
    var height: Int,
    @ColumnInfo(name = "order")
    var order: Int,
    @ColumnInfo(name = "weight")
    var weight: Int,
)
