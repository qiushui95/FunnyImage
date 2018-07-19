package me.yangcx.funnyimage

import android.content.DialogInterface
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.dialog_test.view.*
import top.limuyang2.ldialog.base.BaseLDialog
import top.limuyang2.ldialog.base.OnDialogDismissListener
import top.limuyang2.ldialog.base.ViewHandlerListener
import top.limuyang2.ldialog.base.ViewHolder

class TestDialog : BaseLDialog<TestDialog>() {
    override fun layoutRes(): Int {
        return R.layout.dialog_test
    }

    override fun layoutView(): View? = null

    override fun viewHandler(): ViewHandlerListener? {
        return object : ViewHandlerListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseLDialog<*>) {
                view?.tvss?.setOnClickListener {
                    dismiss()
                }
                getDialog().setOnShowListener {
                    view?.tvss?.animate()
                            ?.rotation(270f)
                            ?.setDuration(1000)
                            ?.start()
                }
                dialog.setDismissListener(object : OnDialogDismissListener() {
                    override fun onDismiss(dialog: DialogInterface?) {
                        Log.e("-======", "onDismiss")
                        view?.tvss?.animate()
                                ?.alpha(0f)
                                ?.setDuration(1000)
                                ?.start()
                    }
                })
                getDialog().setOnCancelListener {
                    Log.e("-======", "setOnCancelListener")
                }
                getDialog().setOnKeyListener { _, keyCode, event ->
                    Log.e("-======", "setOnKeyListener")
                    true
                }
            }
        }
    }

    override fun onDismiss(dialog: DialogInterface?) {

    }

    fun setManager(fragmentManager: FragmentManager): TestDialog {
        setFragmentManager(fragmentManager)
        return this
    }
}