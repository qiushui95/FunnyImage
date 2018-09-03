package me.yangcx.xtoast

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

object XToastUtils {
    fun setDebug(isDebug: Boolean) {
        Holder.instance.setDebug(isDebug)
    }

    private fun createToast(context: Context, message: CharSequence, duration: Int, isDebug: Boolean): Toast {
        val toast = Toast.makeText(context, message, duration)
        toast.setText(if (isDebug) {
            "debug消息\n$message\n出现请联系开发"
        } else {
            message
        })
        toast.duration = duration
        return toast
    }

    fun showShort(context: Context, message: CharSequence) {
        Holder.instance.show(createToast(context, message, Toast.LENGTH_SHORT, false), message, false)
    }

    fun showShortImmediately(context: Context, message: CharSequence) {
        Holder.instance.cancelAll()
        showShort(context, message)
    }

    fun showLong(context: Context, message: CharSequence) {
        Holder.instance.show(createToast(context, message, Toast.LENGTH_LONG, false), message, false)
    }

    fun showLongImmediately(context: Context, message: CharSequence) {
        Holder.instance.cancelAll()
        showLong(context, message)
    }

    fun showDebug(activity: Activity, message: CharSequence) {
        Holder.instance.show(createToast(activity, message, Toast.LENGTH_LONG, true), message, true)
    }

    fun showDebug(fragment: Fragment, message: CharSequence) {
        showDebug(fragment.requireActivity(), message)
    }

    private object Holder {
        val instance = XToast()
    }
}