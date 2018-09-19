package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Transformations
import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xfoundation.viewmodel.BasePagingViewModel
import me.yangcx.xnetwork.entity.RequestResult
import javax.inject.Inject

class HomeViewModel : BasePagingViewModel(20) {
    @Inject
    lateinit var repository: HomeRepository

    init {
        DaggerHolder.viewModelComponent
                .inject(this)
    }


    val dataList by lazy {
        MutableLiveData<RequestResult<ImageDetails>>()
    }

    fun init() {
        dataList.value = RequestResult()
    }

    fun refreshData() {
        dataList.value?.dataList?.clear()
        dataList.value = dataList.value
        getNextPage()
    }

}