package me.yangcx.xnetwork.callback

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import me.yangcx.xnetwork.entity.MultipleStatusResult
import me.yangcx.xnetwork.status.RequestStatus

abstract class MultipleResponseObserver< T, H>(private val data: MutableLiveData<MultipleStatusResult<H>>) : Observer<T> {
    private val value = data.value ?: MultipleStatusResult()
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

    abstract fun onSuccess(value: MultipleStatusResult<H>, result: T)
    open fun onFailed(value: MultipleStatusResult<H>, ex: Throwable?) {
        value.status = RequestStatus.ERROR
        value.errorMessage = ex?.message ?: "未知错误"
    }
}