package me.yangcx.funnyimage.api

import me.yangcx.funnyimage.entity.UnsplashContainer
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getSplashImage(): Call<UnsplashContainer>

    @GET("random")
    fun getImageList(@Query("count") count: Int): Call<List<UnsplashContainer>>
}