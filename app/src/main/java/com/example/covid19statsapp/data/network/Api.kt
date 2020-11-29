package com.example.covid19statsapp.data.network

import com.example.covid19statsapp.data.model.DataClassItem
import com.example.covid19statsapp.data.model.GlobalDataClass
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("countries?sort=country")
    fun getStatistics(): Call<MutableList<DataClassItem>>

    @GET("all")
    fun getGlobalStatistics(): Call<GlobalDataClass>
}