package me.yangcx.xdialog.other

import android.support.v4.app.FragmentManager
import android.view.View

interface XDialogHolder : XDismissMoreHolder {
    fun show(manager: FragmentManager)
    fun hideSoftInput()
    fun hideSoftInput(target: View)
    fun showSoftInput(view: View)
}