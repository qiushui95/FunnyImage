package me.yangcx.xfoundation.viewmodel

import android.arch.lifecycle.MutableLiveData

abstract class BasePagingViewModel constructor(val pageSize: Int, private val startPage: Int = 0) : BaseViewModel() {
    var noMoreData = false
    val hasNext = !noMoreData
    private var itemCount = 0
    val dataList by lazy {
        MutableLiveData<MutableList<Any>>()
    }
    private var currentPage = startPage

    init {
        dataList.observeForever {
            it?.apply {
                noMoreData = size > itemCount
                itemCount = size
                currentPage++
            }
        }
    }

    @Synchronized
    protected fun refreshPaging() {
        synchronized(noMoreData) {
            dataList.value = mutableListOf()
            currentPage = startPage
        }
    }

    protected fun getNextPage() = currentPage
}