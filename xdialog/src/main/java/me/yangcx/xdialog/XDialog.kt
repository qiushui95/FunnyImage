package me.yangcx.xdialog

import android.app.Dialog
import android.content.Context
import android.support.annotation.StyleRes

class XDialog(context: Context,
              @StyleRes themeResId: Int,
              private val beforeCancel: () -> Boolean
) : Dialog(context, themeResId), DismissAction {

    override fun dismissImmediately() {
        super.dismiss()
    }

    override fun cancel() {
        if (beforeCancel()) {
            super.cancel()
        }
    }
}