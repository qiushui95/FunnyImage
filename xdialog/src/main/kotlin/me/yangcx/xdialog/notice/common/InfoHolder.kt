package me.yangcx.xdialog.notice.common

import android.support.v4.app.FragmentManager

interface InfoHolder {
    fun show(manager: FragmentManager)
    fun updateMessage(message: String?)
}