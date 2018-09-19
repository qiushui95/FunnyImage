package me.yangcx.funnyimage.ui.splash

import android.arch.lifecycle.MutableLiveData
import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.xfoundation.viewmodel.BaseViewModel
import javax.inject.Inject

class SplashViewModel : BaseViewModel() {
    @Inject
    lateinit var repository: SplashRepository

    init {
        DaggerHolder.viewModelComponent
                .inject(this)
    }

    val splashImage by lazy {
        repository.getSplashImage(status)
    }

    val collectStatus by lazy {
        MutableLiveData<Boolean>()
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

    fun collect() {
        splashImage.value?.id?.also {
            repository.collectImage(it, collectStatus)
        }
    }
}