package me.yangcx.xdialog

import android.support.annotation.ColorInt
import android.support.v4.app.FragmentManager
import me.yangcx.xdialog.listener.DismissedListener
import me.yangcx.xdialog.listener.OnShowListener
import me.yangcx.xdialog.listener.PreDismissListener

interface XDialogFragmentBuilder {
    fun setWidth(width: Int, isDp: Boolean = false): XDialogFragmentBuilder
    fun setHeight(height: Int, isDp: Boolean = false): XDialogFragmentBuilder
    fun setBackColor(@ColorInt backColor: Int): XDialogFragmentBuilder
    fun setOutsideCancelable(outsideCancelable: Boolean): XDialogFragmentBuilder
    fun setBackPressable(backPressable: Boolean): XDialogFragmentBuilder
    fun addShowListener(listener: OnShowListener): XDialogFragmentBuilder
    fun addPreDismissListener(listener: PreDismissListener): XDialogFragmentBuilder
    fun addDismissedListener(listener: DismissedListener): XDialogFragmentBuilder
    fun showImmediately(manager: FragmentManager)
    fun showImmediately(manager: FragmentManager, showTag: String)
    fun dismissImmediately()
}