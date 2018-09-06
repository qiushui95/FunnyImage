package me.yangcx.funnyimage.repository

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.entity.UnsplashContainer
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject
import kotlin.concurrent.thread

@RepositoryScope
class SplashRepository @Inject constructor(private val retrofit: ApiConfig, private val dao: FunnyDao) {

    fun getSplashImage(splashImage: MutableLiveData<ImageInfo>) {
        retrofit.getSplashImage()
                .enqueue(object : Callback<UnsplashContainer> {
                    override fun onFailure(call: Call<UnsplashContainer>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<UnsplashContainer>, response: Response<UnsplashContainer>) {
                        val imageInfo = response.body()?.convertToImageInfo()
                        splashImage.value = imageInfo
                        thread {
                            dao.insertImage(imageInfo)
                        }
                    }
                })
    }
}