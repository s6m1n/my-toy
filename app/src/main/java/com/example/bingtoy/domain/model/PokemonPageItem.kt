package com.example.bingtoy.domain.model

data class PokemonPageItem(
    val id: Long,
    val name: String?,
    val imageUrl: String,
    val imagePath: String? = null,
    val isFavorite: Boolean = false,
)
