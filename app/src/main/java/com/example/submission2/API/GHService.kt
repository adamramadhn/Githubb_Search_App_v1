package com.example.submission2.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object GHService {
    val servis =
        Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(GHInterface::class.java)
}