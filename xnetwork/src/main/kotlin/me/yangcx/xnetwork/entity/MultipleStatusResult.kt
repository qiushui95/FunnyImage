package me.yangcx.xnetwork.entity

import me.yangcx.xnetwork.status.RequestStatus

class MultipleStatusResult<T> {
    var status = RequestStatus.SUCCESS
    var errorMessage: String? = null
    val dataList: MutableList<T> = mutableListOf()

    fun isLoading() = status == RequestStatus.LOADING
    fun isNotLoading() = !isLoading()
    fun isSuccess() = status == RequestStatus.SUCCESS
    fun isFailed() = status == RequestStatus.ERROR
}