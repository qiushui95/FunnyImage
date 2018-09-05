package me.yangcx.funnyimage.api

import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.funnyimage.reponse.MultipleResult
import me.yangcx.funnyimage.reponse.SingleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiConfig {
    @GET("random")
    fun getSplashImage(@Query("w") width: Int, @Query("h") height: Int): Call<SingleResult<UnsplashContainer>>

    @GET("random")
    fun getImageList(@Query("count") count: Int): Call<MultipleResult<UnsplashContainer>>
}