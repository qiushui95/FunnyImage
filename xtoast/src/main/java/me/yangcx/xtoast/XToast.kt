package me.yangcx.xtoast

import android.view.View
import android.widget.Toast

internal class XToast {
    private var current: Toast? = null
    private var isDebug: Boolean = false
    private val toastList by lazy {
        mutableListOf<Pair<CharSequence, Toast>>()
    }

    fun setDebug(isDebug: Boolean) {
        this.isDebug = isDebug
    }

    fun show(toast: Toast, message: CharSequence, isDebug: Boolean) {
        if (!isDebug || this.isDebug == isDebug) {
            toast.view.addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
                override fun onViewDetachedFromWindow(v: View) {
                    toastList.removeAll {
                        toast == it.second
                    }
                    v.removeOnAttachStateChangeListener(this)
                    current = null
                }

                override fun onViewAttachedToWindow(v: View) {
                    current = toast
                }
            })
        }
        toastList.add(Pair(message, toast))
        toast.show()
    }

    fun cancel(message: CharSequence) {
        toastList.filter {
            it.first == message
        }.map {
            it.second
        }.forEach {
            it.cancel()
        }
    }

    fun cancelCurrent() {
        current?.cancel()
    }

    fun cancelAll() {
        toastList.map {
            it.second
        }.forEach {
            it.cancel()
        }
    }
}