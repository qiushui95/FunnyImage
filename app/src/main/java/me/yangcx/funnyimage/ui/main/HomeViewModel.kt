package me.yangcx.funnyimage.ui.main

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.forrecyclerview.adapter.BaseDataAdapter
import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.xnetwork.entity.MultipleStatusResult
import javax.inject.Inject

class HomeViewModel : ViewModel() {
    @Inject
    lateinit var repository: HomeRepository
    @Inject
    lateinit var adapter: BaseDataAdapter

    init {
        DaggerHolder.viewModelComponent
                .inject(this)
    }


    val dataList by lazy {
        MutableLiveData<MultipleStatusResult<ImageDetails>>()
    }

    fun init() {
        dataList.value = MultipleStatusResult()
    }

    fun refreshData() {
        dataList.value?.dataList?.clear()
        dataList.value = dataList.value
        getNextPage()
    }

    fun getNextPage() {
        repository.getNextPageImage(dataList)
    }
}