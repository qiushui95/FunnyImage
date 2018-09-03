package me.yangcx.xdialog.base

import android.app.Dialog
import android.content.Context
import android.support.annotation.StyleRes
import android.support.v4.app.DialogFragment
import java.lang.ref.WeakReference

internal class BaseDialog(context: Context, @StyleRes themeResId: Int, fragment: DialogFragment) : Dialog(context, themeResId) {
    private val superFragmentReference by lazy {
        WeakReference(fragment)
    }

    override fun cancel() {
        val fragment = superFragmentReference.get()
        if (fragment == null) {
            super.cancel()
        } else {
            fragment.onCancel(this)
            fragment.dismiss()
        }
    }
}