package me.yangcx.funnyimage.aac.model

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.funnyimage.aac.repository.SplashRepository
import me.yangcx.funnyimage.entity.ImageInfo
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    private  val repository: SplashRepository by lazy { SplashRepository() }
    val splashImage by lazy {
        MutableLiveData<ImageInfo>()
    }

    fun init(width: Int, height: Int) {
        repository.getSplashImage(width, height,splashImage)
    }
}