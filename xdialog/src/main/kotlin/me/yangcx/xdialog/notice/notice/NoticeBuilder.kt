package me.yangcx.xdialog.notice.notice

import android.content.Context
import android.support.annotation.CheckResult
import kotlinx.android.synthetic.main.xdialog_info.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.notice.common.*
import me.yangcx.xdialog.other.XDialogBuilder
import me.yangcx.xdialog.utils.DialogAnimationUtils

class NoticeBuilder(private val context: Context) : InfoBuilder<NoticeBuilder>() {
    private var dismissDelay = 1500L
    private var noticeType = NoticeType.INFO
    override fun self(): NoticeBuilder {
        return this
    }

    @CheckResult
    fun setDismissDelay(dismissDelay: Long): NoticeBuilder {
        this.dismissDelay = dismissDelay
        return this
    }

    @CheckResult
    fun setInfoType(noticeType: NoticeType): NoticeBuilder {
        this.noticeType = noticeType
        return this
    }

    @CheckResult
    fun build(): NoticeHolder {
        val infoConfig = InfoConfig(InfoType.NOTICE, onCanceledListener, message, 0f, noticeType, dismissDelay)
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