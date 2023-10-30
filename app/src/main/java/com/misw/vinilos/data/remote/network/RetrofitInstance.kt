package com.misw.vinilos.data.remote.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://backend-misw4203.onrender.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
