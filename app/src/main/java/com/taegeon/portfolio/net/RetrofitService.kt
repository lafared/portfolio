package com.taegeon.portfolio.net

import com.taegeon.portfolio.data.ImgData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface RetrofitService {
    @Headers("Authorization: KakaoAK 3591f84433a8616bcd69bc953be6f170")
    @GET("/v2/search/image")
    fun requestSearchImg(
        @Query("query") keyword: String,
        @Query("sort") sort: String = "accuracy",
        @Query("page") page: Int = 1,
        @Query("size") size: Int = 30
    ): Call<ImgData>
}