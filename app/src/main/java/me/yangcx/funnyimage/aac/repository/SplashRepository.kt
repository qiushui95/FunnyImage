package me.yangcx.funnyimage.aac.repository

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.funnyimage.reponse.SingleResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiConfig) {

    fun getSplashImage(splashImage: MutableLiveData<ImageInfo>) {
        retrofit.getSplashImage()
                .enqueue(object : Callback<UnsplashContainer> {
                    override fun onFailure(call: Call<UnsplashContainer>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<UnsplashContainer>, response: Response<UnsplashContainer>) {
                        splashImage.value = response.body()?.convertToImageInfo()
                    }
                })
    }
}