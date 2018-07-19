package me.yangcx.funnyimage

import android.util.Log
import android.view.View
import me.yangcx.xdialog.DismissAction
import me.yangcx.xdialog.XDialogFragment
import me.yangcx.xdialog.listener.PreDismissListener
import kotlinx.android.synthetic.main.dialog_test.view.*

class MyDialog : XDialogFragment(R.layout.dialog_test) {
    override fun initThis(view: View) {
        view.tvss.setOnClickListener {
            dismiss()
        }
        addPreDismissListener(object : PreDismissListener {
            override fun preDismiss(parent: View, dismissAction: DismissAction): Boolean {
                Log.e("===========", "preDismiss")
                parent.tvss.animate()
                        .alpha(0f)
                        .rotationBy(180f)
                        .setDuration(2000)
                        .withEndAction {
                            dismissAction.dismissImmediately()
                        }
                        .start()
                return true
            }
        })
    }

}