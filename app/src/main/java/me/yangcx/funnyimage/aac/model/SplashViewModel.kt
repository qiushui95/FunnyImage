package me.yangcx.funnyimage.aac.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.funnyimage.aac.repository.SplashRepository
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.entity.ImageInfo
import retrofit2.Retrofit
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    @Inject
    lateinit var repository: SplashRepository

    init {
        FunnyApplication.repositoryComponent
                .inject(this)

    }

    val splashImage by lazy {
        MutableLiveData<ImageInfo>()
    }

    fun getSplashImage() {
        repository.getSplashImage(splashImage)
    }
}