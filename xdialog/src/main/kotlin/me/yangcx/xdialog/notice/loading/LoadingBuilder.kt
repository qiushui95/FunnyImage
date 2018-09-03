package me.yangcx.xdialog.notice.loading

import android.content.Context
import android.support.annotation.CheckResult
import kotlinx.android.synthetic.main.xdialog_info.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.notice.common.*
import me.yangcx.xdialog.other.XDialogBuilder
import me.yangcx.xdialog.utils.DialogAnimationUtils

class LoadingBuilder(private val context: Context) : InfoBuilder<LoadingBuilder>() {
    override fun self(): LoadingBuilder {
        return this
    }

    @CheckResult
    fun build(): LoadingHolder {
        val infoConfig = InfoConfig(InfoType.LOADING, onCanceledListener, message, 0f, NoticeType.INFO, -1)
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