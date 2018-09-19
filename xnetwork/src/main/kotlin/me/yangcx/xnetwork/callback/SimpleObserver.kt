package me.yangcx.xnetwork.callback

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

abstract class SimpleObserver<T> : Observer<T> {
    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        Log.e("===SimpleObserver===", e.message ?: "未知错误")
    }

    override fun onComplete() {

    }

    abstract fun onSuccess(result: T)
}