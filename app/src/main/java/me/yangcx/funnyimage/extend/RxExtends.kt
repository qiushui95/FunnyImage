package me.yangcx.funnyimage.extend

import android.arch.lifecycle.MutableLiveData
import io.reactivex.Maybe
import io.reactivex.MaybeObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum

inline fun<reified T> Maybe<T>.resultOnUi(): Maybe<T> {
    return subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
}

inline fun <reified T, H> Maybe<T>.subscribeStatus(data: MutableLiveData<SingleStatusResult<H>>, crossinline onSuccess: (value: SingleStatusResult<H>, result: T) -> Unit) {
    val value = data.value ?: SingleStatusResult()
    subscribe(object : MaybeObserver<T> {
        override fun onSubscribe(d: Disposable) {
            value.status = StatusEnum.LOADING
            data.value = value
        }

        override fun onSuccess(t: T) {
            onSuccess(value, t)
        }

        override fun onError(e: Throwable) {
            value.status = StatusEnum.ERROR
            value.errorMessage = e.message
        }

        override fun onComplete() {
            data.value = data.value
        }
    })
}