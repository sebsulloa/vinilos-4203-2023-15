package com.misw.vinilos.data.local.entities

import androidx.room.Entity
@Entity(tableName = "tracks", primaryKeys = ["id", "albumId"])
data class TrackEntity(
    val id: Int,
    val name: String,
    val duration: String,
    val albumId: Int
)