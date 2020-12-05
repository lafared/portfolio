package com.taegeon.portfolio.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object DaumApiManager {
    private val retrofit = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getService(): RetrofitService = retrofit.create(RetrofitService::class.java)
}