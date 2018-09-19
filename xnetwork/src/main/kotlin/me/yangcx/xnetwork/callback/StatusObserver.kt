package me.yangcx.xnetwork.callback

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.yangcx.xnetwork.entity.RequestResult
import me.yangcx.xnetwork.status.RequestStatus

abstract class StatusObserver<T>(private val status: MutableLiveData<RequestResult>) : Observer<T> {
    override fun onSubscribe(d: Disposable) {
        status.value = RequestResult.newLoading()
    }

    override fun onNext(t: T) {
        onSuccess(status, t)
    }

    override fun onError(e: Throwable) {
        onFailed(status, e)
    }

    override fun onComplete() {

    }

    abstract fun onSuccess(status: MutableLiveData<RequestResult>, result: T)
    open fun onFailed(status: MutableLiveData<RequestResult>, e: Throwable) {
        status.value = RequestResult.newFailed(e.message ?: "未知错误")
    }
}