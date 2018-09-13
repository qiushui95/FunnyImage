package me.yangcx.xnetwork.callback

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.yangcx.xnetwork.entity.SingleStatusResult
import me.yangcx.xnetwork.status.RequestStatus

abstract class SingleResponseObserver<T, H>(private val data: MutableLiveData<SingleStatusResult<H>>) : Observer<T> {
    private val value = data.value ?: SingleStatusResult()
    override fun onSubscribe(d: Disposable) {
        value.status = RequestStatus.LOADING
        data.value = value
    }

     override fun onNext(result: T) {
        onSuccess(value, result)
    }

    override fun onError(e: Throwable) {
        onFailed(value, e)
    }

     override fun onComplete() {
        data.value = value
    }

    abstract fun onSuccess(value: SingleStatusResult<H>, result: T)
    open fun onFailed(value: SingleStatusResult<H>, ex: Throwable?) {
        value.status = RequestStatus.ERROR
        value.errorMessage = ex?.message ?: "未知错误"
    }
}