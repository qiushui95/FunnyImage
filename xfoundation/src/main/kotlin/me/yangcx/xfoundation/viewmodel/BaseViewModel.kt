package me.yangcx.xfoundation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.xnetwork.entity.RequestResult

abstract class BaseViewModel : ViewModel() {
    val status by lazy {
        MutableLiveData<RequestResult>()
    }
}