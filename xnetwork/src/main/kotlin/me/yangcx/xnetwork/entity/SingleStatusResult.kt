package me.yangcx.xnetwork.entity

import me.yangcx.xnetwork.status.RequestStatus

class SingleStatusResult<T> {
    var status = RequestStatus.SUCCESS
    var errorMessage: String? = null
    var data: T? = null

    fun isLoading() = status == RequestStatus.LOADING
    fun isNotLoading() = !isLoading()
}