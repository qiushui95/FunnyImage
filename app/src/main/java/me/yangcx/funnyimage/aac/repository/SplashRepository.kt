package me.yangcx.funnyimage.aac.repository

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import me.yangcx.funnyimage.reponse.SingleResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SplashRepository @Inject constructor(private val retrofit: ApiConfig) {
    fun getSplashImage(width: Int, height: Int, splashImage: MutableLiveData<ImageInfo>) {

        retrofit.getSplashImage(width, height)
                .enqueue(object : Callback<SingleResult<UnsplashContainer>> {
                    override fun onFailure(call: Call<SingleResult<UnsplashContainer>>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<SingleResult<UnsplashContainer>>, response: Response<SingleResult<UnsplashContainer>>) {
                        splashImage.value = response.body()?.data?.convertToImageInfo()
                    }
                })
    }
}