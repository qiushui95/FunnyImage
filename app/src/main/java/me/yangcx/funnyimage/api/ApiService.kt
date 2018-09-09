package me.yangcx.funnyimage.api

import io.reactivex.Maybe
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.funnyimage.http.MultipleStatusResult
import me.yangcx.funnyimage.http.SingleStatusResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getSplashImage(): Maybe<UnsplashContainer>

    @GET("random")
    fun getImageList(@Query("count") count: Int): Maybe<UnsplashContainer>
}