package com.example.covid19statsapp.data.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {
    const val BASE_URL: String = "https://corona.lmao.ninja/v2/"

    val retrofitBuilder: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    val apiService: Api by lazy{
        retrofitBuilder
            .build()
            .create(Api::class.java)
    }
}