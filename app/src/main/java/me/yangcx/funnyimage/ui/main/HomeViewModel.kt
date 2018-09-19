package me.yangcx.funnyimage.ui.main

import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.xfoundation.viewmodel.BasePagingViewModel
import javax.inject.Inject

class HomeViewModel : BasePagingViewModel(20) {
    @Inject
    lateinit var repository: HomeRepository

    init {
        DaggerHolder.viewModelComponent
                .inject(this)
    }


    fun refreshData() {
        refreshPaging()
        getData()
        dataList
    }

    fun getData() {
        repository.getNextPageImage(getNextPage(), pageSize, dataList, status)
    }
}