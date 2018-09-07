package me.yangcx.funnyimage.ui.view

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.repository.SplashRepository
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