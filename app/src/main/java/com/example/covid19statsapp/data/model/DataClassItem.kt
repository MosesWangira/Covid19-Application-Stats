package com.example.covid19statsapp.data.model

data class DataClassItem (
    val country: String,

    val cases: Int,
    val recovered: Int,
    val deaths: Int,

    val todayCases: Int,
    val todayRecovered: Int,
    val todayDeaths: Int,

    val critical: Int,
    val tests: Int,
    val updated: Long,

    val continent: String
)