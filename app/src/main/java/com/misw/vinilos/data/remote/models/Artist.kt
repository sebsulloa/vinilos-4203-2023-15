package com.misw.vinilos.data.remote.models

data class Artist(
    val id: Int,
    val name: String,
    val image: String,
    val description: String,
    val birthDate: String,
    val albums: List<Album>
)