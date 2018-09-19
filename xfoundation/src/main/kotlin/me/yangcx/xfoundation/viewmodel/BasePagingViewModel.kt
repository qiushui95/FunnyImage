package me.yangcx.xfoundation.viewmodel

import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import me.yangcx.xnetwork.entity.RequestResult

abstract class BasePagingViewModel constructor(val pageSize: Int, private val startPage: Int = 0) : ViewModel() {
    var noMoreData = false
    val hasNext = !noMoreData

    private var currentPage = startPage
    val status by lazy {
        MutableLiveData<RequestResult>()
    }

    @Synchronized
    protected fun loadSuccess(dataList: List<*>) {
        synchronized(noMoreData) {
            noMoreData = dataList.size < pageSize
            currentPage++
        }
    }

    @Synchronized
    protected fun refresh() {
        synchronized(noMoreData) {
            noMoreData = false
            currentPage = startPage
        }
    }

    fun getNextPage() = currentPage
}