package me.yangcx.xdialog.notice.progress

import android.content.Context
import android.support.annotation.CheckResult
import kotlinx.android.synthetic.main.xdialog_info.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.notice.common.*
import me.yangcx.xdialog.other.XDialogBuilder
import me.yangcx.xdialog.utils.DialogAnimationUtils

class ProgressBuilder(private val context: Context) : InfoBuilder<ProgressBuilder>() {
    override fun self(): ProgressBuilder {
        return this
    }

    @CheckResult
    fun build(): ProgressHolder {
        val infoConfig = InfoConfig(InfoType.PROGRESS, onCanceledListener, message, 0f, NoticeType.INFO, 0L)
        val baseBuilder = XDialogBuilder(R.layout.xdialog_info)
                .setInAnimation {
                    DialogAnimationUtils.fadeIn(it.llInfo)
                }.setOutAnimation { view, holder ->
                    DialogAnimationUtils.fadeOut(view.llInfo, holder)
                }
        val baseConfig = baseBuilder.setWidth(context.resources.getDimension(R.dimen.dimenDialogInfoWidth), DimensionEnum.PX)
                .setCanceledOnTouchOutside(canceledOnTouchOutside)
                .setCancelable(cancelable)
                .setDimAmount(dimAmount)
                .buildConfig()
        return InfoDialog.getInstance(baseConfig, infoConfig)
    }
}