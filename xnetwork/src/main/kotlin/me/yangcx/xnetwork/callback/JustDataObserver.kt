package me.yangcx.xnetwork.callback

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class JustDataObserver<T, R>(private val data: MutableLiveData<R>) : Observer<T> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        onSuccess(data, t)
    }

    override fun onError(e: Throwable) {
        Log.e("===JustDataObserver===", e.message ?: "未知错误")
    }

    override fun onComplete() {

    }

    abstract fun onSuccess(data: MutableLiveData<R>, result: T)
}