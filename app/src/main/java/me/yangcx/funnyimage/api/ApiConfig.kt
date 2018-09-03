package me.yangcx.funnyimage.api

import android.widget.ImageView
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.reponse.MultipleResult
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiConfig {
    @GET("meituApi")
    fun getImageList(@Query("page") page: Int): Call<MultipleResult<ImageInfo>>
}