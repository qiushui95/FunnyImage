package me.yangcx.xdialog.listener

import android.view.View
import me.yangcx.xdialog.DismissAction

interface PreDismissListener {
    fun preDismiss(parent: View, dismissAction: DismissAction): Boolean
}