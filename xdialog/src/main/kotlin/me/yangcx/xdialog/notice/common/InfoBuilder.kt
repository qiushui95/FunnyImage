package me.yangcx.xdialog.notice.common

import android.support.annotation.CheckResult
import android.support.annotation.FloatRange

abstract class InfoBuilder<T : InfoBuilder<T>> {
    protected var onCanceledListener: InfoCanceledListener? = null
    protected var dimAmount = 0.4f
    protected var canceledOnTouchOutside = true
    protected var cancelable = true
    protected var message: String? = null

    abstract fun self(): T

    @CheckResult
    fun setOnCanceled(onCacceled: (infoType: InfoType?) -> Unit): T {
        this.onCanceledListener = object : InfoCanceledListener() {
            override fun onCanceled(infoType: InfoType?) {
                onCacceled(infoType)
            }
        }
        return self()
    }

    @CheckResult
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0, fromInclusive = true, toInclusive = true) dimAmount: Float): T {
        this.dimAmount = dimAmount
        return self()
    }

    @CheckResult
    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): T {
        this.canceledOnTouchOutside = canceledOnTouchOutside
        return self()
    }

    @CheckResult
    fun setCancelable(cancelable: Boolean): T {
        this.cancelable = cancelable
        return self()
    }

    @CheckResult
    fun setMessage(message: String): T {
        this.message = message
        return self()
    }
}