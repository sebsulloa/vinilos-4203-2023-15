package com.misw.vinilos.data.remote.models

import com.misw.vinilos.data.local.entities.CollectorEntity

data class Collector(
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val favoritePerformers: List<Artist>
) {
    fun toCollectorEntity(): CollectorEntity {
        return CollectorEntity(
            id = id,
            name = name,
            telephone = telephone,
            email = email,
        )
    }
}