package me.yangcx.funnyimage.api

import io.reactivex.Observable
import me.yangcx.funnyimage.entity.UnsplashContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getSplashImage(): Observable<UnsplashContainer>

    @GET("random")
    fun getImageList(@Query("count") count: Int): Observable<List<UnsplashContainer>>
}