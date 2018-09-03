package me.yangcx.xdialog.ask.sheet

import android.content.Context
import android.support.annotation.ColorInt
import android.support.v4.content.ContextCompat
import android.view.Gravity
import kotlinx.android.synthetic.main.xdialog_sheet.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.ask.common.AskBuilder
import me.yangcx.xdialog.ask.common.AskHolder
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.other.XDialogBuilder
import me.yangcx.xdialog.utils.DefaultConfig
import me.yangcx.xdialog.utils.DialogAnimationUtils
import me.yangcx.xdialog.utils.DimensionUtils

class SheetBuilder(context: Context) : AskBuilder<SheetBuilder>(context) {
    private var cancelText: TextConfig = TextConfig(context.getString(R.string.stringDialogCancel), ContextCompat.getColor(context, R.color.colorDialogButtonCancel), true,DefaultConfig.DEFAULT_BUTTON_SIZE, DimensionEnum.SP)
    override val MAX_BUTTON_COUNT: Int = 6

    override fun self(): SheetBuilder {
        return this
    }

    fun setCancelText(cancelText: CharSequence, @ColorInt textColor: Int = ContextCompat.getColor(context, R.color.colorDialogButtonCancel), textSize: Float = DefaultConfig.DEFAULT_TITLE_SIZE, dimensionEnum: DimensionEnum = DimensionEnum.SP, isBold: Boolean = true): SheetBuilder {
        this.cancelText = TextConfig(cancelText, textColor, isBold, textSize, dimensionEnum)
        return this
    }

    override fun build(): AskHolder {
        val baseBuilder = XDialogBuilder(R.layout.xdialog_sheet)
                .setInAnimation {
                    DialogAnimationUtils.bottomIn(it.llSheet)
                }.setOutAnimation { view, holder ->
                    DialogAnimationUtils.bottomOut(view.llSheet, holder)
                }
        val baseConfig = baseBuilder.setWidthPercent(1f)
                .setCanceledOnTouchOutside(canceledOnTouchOutside)
                .setCancelable(cancelable)
                .setDimAmount(dimAmount)
                .setGravity(Gravity.BOTTOM)
                .buildConfig()
        val alertConfig = SheetConfig(title, message, cancelText, clickListener)
        alertConfig.buttonList.addAll(buttonList)
        return SheetDialog.getInstance(alertConfig, baseConfig)
    }

    companion object {
        const val CANCEL_INDEX = -1
    }
}