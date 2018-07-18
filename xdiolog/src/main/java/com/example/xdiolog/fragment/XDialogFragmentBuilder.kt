package com.example.xdiolog.fragment

import android.support.annotation.ColorInt
import android.support.v4.app.FragmentManager
import com.example.xdiolog.fragment.listener.DismissedListener
import com.example.xdiolog.fragment.listener.OnShowListener
import com.example.xdiolog.fragment.listener.PreDismissListener

interface XDialogFragmentBuilder {
    fun setWidth(width: Int, isDp: Boolean = false): XDialogFragmentBuilder
    fun setHeight(height: Int, isDp: Boolean = false): XDialogFragmentBuilder
    fun setBackColor(@ColorInt backColor: Int): XDialogFragmentBuilder
    fun setOutsideTouchable(outsideTouchable: Boolean): XDialogFragmentBuilder
    fun setBackPressable(backPressable: Boolean): XDialogFragmentBuilder
    fun addShowListener(listener: OnShowListener): XDialogFragmentBuilder
    fun addPreDismissListener(listener: PreDismissListener): XDialogFragmentBuilder
    fun addDismissedListener(listener: DismissedListener): XDialogFragmentBuilder
    fun showImmediately(manager: FragmentManager)
    fun showImmediately(manager: FragmentManager, showTag: String)
    fun dismissImmediately()
}