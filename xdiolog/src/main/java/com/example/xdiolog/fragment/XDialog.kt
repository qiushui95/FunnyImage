package com.example.xdiolog.fragment

import android.app.Dialog
import android.content.Context
import android.support.annotation.StyleRes
import android.view.View
import com.example.xdiolog.fragment.listener.DismissedListener
import com.example.xdiolog.fragment.listener.OnShowListener
import com.example.xdiolog.fragment.listener.PreDismissListener

class XDialog(context: Context,
              @StyleRes themeResId: Int,
              private val onShowListenerList: List<OnShowListener>,
              private val preDismissListenerList: MutableList<PreDismissListener>,
              private val dismissedListenerList: List<DismissedListener>
) : Dialog(context, themeResId), DismissAction {
    override fun show() {
        super.show()
        window?.decorView?.apply {
            onShowListenerList.forEach {
                it.onShow(this)
            }
        }
    }

    override fun dismissImmediately() {
        super.dismiss()
    }

    override fun dismiss() {
        val parent = window?.decorView
        if (parent is View) {
            var dismissImmediately = true
            preDismissListenerList.toList()
                    .forEach {
                        if (it.preDismiss(parent, this)) {
                            dismissImmediately = false
                        }
                        preDismissListenerList.remove(it)
                    }
            if (dismissImmediately) {
                dismissImmediately()
            }
        }
        if (parent !is View) {
            dismissImmediately()
        }
    }

    override fun onStop() {
        super.onStop()
        dismissedListenerList.forEach {
            it.onDismiss()
        }
    }
}