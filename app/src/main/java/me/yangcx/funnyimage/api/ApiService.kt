package me.yangcx.funnyimage.api

import io.reactivex.Observable
import me.yangcx.funnyimage.entity.UnsplashContainer
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("random")
    fun getSplashImage(): Observable<UnsplashContainer>

    @GET("/photos?order_by=latest")
    fun getImageList(@Query("page") page: Int, @Query("per_page") perPage: Int): Observable<List<UnsplashContainer>>
}