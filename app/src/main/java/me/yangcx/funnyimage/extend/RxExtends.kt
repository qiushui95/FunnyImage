package me.yangcx.funnyimage.extend

import android.arch.lifecycle.MutableLiveData
import io.reactivex.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import org.reactivestreams.Subscription

inline fun <reified T> Single<T>.resultOnUi() = subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())


fun Completable.resultOnUi() = subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

inline fun <reified T> Maybe<T>.resultOnUi() = subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

inline fun <reified T> Observable<T>.resultOnUi() = subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

inline fun <reified T> Flowable<T>.resultOnUi() = subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())

inline fun <reified T, H> Observable<T>.subscribeStatus(data: MutableLiveData<SingleStatusResult<H>>, crossinline onSuccess: (value: SingleStatusResult<H>, result: T) -> Unit) {
    val value = data.value ?: SingleStatusResult()
    subscribe(object : Observer<T> {
        override fun onSubscribe(d: Disposable) {
            value.status = StatusEnum.LOADING
            data.value = value
        }

        override fun onNext(t: T) {
            onSuccess(value, t)
        }

        override fun onError(e: Throwable) {
            value.status = StatusEnum.ERROR
            value.errorMessage = e.message
        }

        override fun onComplete() {
            data.postValue(value)
        }
    })
}
