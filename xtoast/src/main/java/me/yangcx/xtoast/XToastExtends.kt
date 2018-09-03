package me.yangcx.xtoast

import android.app.Activity
import android.content.Context
import android.support.v4.app.Fragment

fun Context.toastShort(message: CharSequence) {
    XToastUtils.showShort(this, message)
}

fun Fragment.toastShort(message: CharSequence) {
    requireContext().toastShort(message)
}

fun Context.toastShortImmediately(message: CharSequence) {
    XToastUtils.showShortImmediately(this, message)
}

fun Fragment.toastShortImmediately(message: CharSequence) {
    requireContext().toastShortImmediately(message)
}

fun Context.toastLong(message: CharSequence) {
    XToastUtils.showLong(this, message)
}

fun Fragment.toastLong(message: CharSequence) {
    requireContext().toastLong(message)
}

fun Context.toastLongImmediately(message: CharSequence) {
    XToastUtils.showLongImmediately(this, message)
}

fun Fragment.toastLongImmediately(message: CharSequence) {
    requireContext().toastLongImmediately(message)
}

fun Activity.toastDebug(message: CharSequence) {
    XToastUtils.showDebug(this, message)
}

fun Fragment.toastDebug(message: CharSequence) {
    XToastUtils.showDebug(this, message)
}