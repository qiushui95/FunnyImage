package me.yangcx.funnyimage.ui.splash

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.xnetwork.entity.SingleStatusResult
import javax.inject.Inject

class SplashViewModel : ViewModel() {
    @Inject
    lateinit var repository: SplashRepository

    init {
        DaggerHolder.viewModelComponent
                .inject(this)
    }

    val splashImage by lazy {
        MutableLiveData<SingleStatusResult<ImageInfo>>()
    }

    val collectStatus by lazy {
        MutableLiveData<SingleStatusResult<Boolean>>()
    }

    val buttonsIsHiding by lazy {
        MutableLiveData<Boolean>()
    }
    val imageClickable by lazy {
        MutableLiveData<Boolean>()
    }

    fun toggleButtonStatus() {
        buttonsIsHiding.value = buttonsIsHiding.value != true
    }

    fun setImageClickable(clickable: Boolean) {
        if (imageClickable.value != clickable) {
            imageClickable.value = clickable
        }
    }

    fun getSplashImage() {
        repository.getSplashImage(splashImage)
    }

    fun collect() {
        splashImage.value?.data?.id?.also {
            repository.collectImage(it, collectStatus)
        }
    }
}