package me.yangcx.xdialog.ask.alert

import android.content.Context
import android.support.annotation.CheckResult
import kotlinx.android.synthetic.main.xdialog_alert.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.ask.common.AskBuilder
import me.yangcx.xdialog.ask.common.AskHolder
import me.yangcx.xdialog.other.XDialogBuilder
import me.yangcx.xdialog.utils.DialogAnimationUtils

class AlertBuilder(context: Context) : AskBuilder<AlertBuilder>(context) {
    protected override val MAX_BUTTON_COUNT = 4

    override fun self(): AlertBuilder {
        return this
    }

    @CheckResult
    override fun build(): AskHolder {
        val baseBuilder = XDialogBuilder(R.layout.xdialog_alert)
                .setInAnimation {
                    DialogAnimationUtils.fadeIn(it.llAlert)
                }.setOutAnimation { view, holder ->
                    DialogAnimationUtils.fadeOut(view.llAlert, holder)
                }
        val baseConfig = baseBuilder.setWidthPercent(0.71f)
                .setCanceledOnTouchOutside(canceledOnTouchOutside)
                .setCancelable(cancelable)
                .setDimAmount(dimAmount)
                .buildConfig()
        val alertConfig = AlertConfig(title, message, clickListener)
        alertConfig.buttonList.addAll(buttonList)
        return AlertDialog.getInstance(alertConfig, baseConfig)
    }
}