package me.yangcx.xnetwork.entity

import me.yangcx.xnetwork.status.RequestStatus

class MultipleStatusResult<T> {
    var status = RequestStatus.SUCCESS
    var errorMessage: String? = null
    val data: MutableList<T> = mutableListOf()

    fun isLoading() = status == RequestStatus.LOADING
    fun isNotLoading() = !isLoading()
}