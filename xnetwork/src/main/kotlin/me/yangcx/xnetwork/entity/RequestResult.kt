package me.yangcx.xnetwork.entity

import me.yangcx.xnetwork.status.RequestStatus

class RequestResult private constructor(status: RequestStatus, val message: String) {
    private constructor(status: RequestStatus) : this(status, "")

    val isLoading = status == RequestStatus.LOADING
    val isNotLoading = !isLoading
    val isSuccess = status == RequestStatus.SUCCESS
    val isFailed = status == RequestStatus.ERROR

    companion object {
        fun newLoading() = RequestResult(RequestStatus.LOADING)
        fun newSuccess() = RequestResult(RequestStatus.SUCCESS)
        fun newFailed(message: String) = RequestResult(RequestStatus.ERROR, message)
    }
}