package com.misw.vinilos.data.remote.models

data class Track(
    val id: Int,
    val name: String,
    val duration: String
)

data class TrackForm(
    val name: String,
    val minutes: Int,
    val seconds: Int
)