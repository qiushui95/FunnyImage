package me.yangcx.xdialog.ask.common

import android.support.v4.app.FragmentManager


interface AskHolder {
    fun show(manager: FragmentManager)
    fun dismissNow()
    fun dismissNow(onDismissed: () -> Unit)
}