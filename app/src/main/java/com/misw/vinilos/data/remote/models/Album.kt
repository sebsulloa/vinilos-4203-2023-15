package com.misw.vinilos.data.remote.models

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: String,
    val recordLabel: String,
    val tracks: List<Track>,
    val performers: List<Artist>
)