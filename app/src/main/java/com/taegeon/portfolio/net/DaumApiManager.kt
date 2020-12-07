package com.taegeon.portfolio.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DaumApiManager() {
    val retrofitService: RetrofitService = Retrofit.Builder()
        .baseUrl("https://dapi.kakao.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
}