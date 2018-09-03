package me.yangcx.xdialog.ask.common

import android.content.Context
import android.support.annotation.CheckResult
import android.support.annotation.ColorInt
import android.support.annotation.FloatRange
import android.support.v4.content.ContextCompat
import me.yangcx.xdialog.R
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_BUTTON_SIZE
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_MESSAGE_COLOR
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_MESSAGE_SIZE
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_TITLE_COLOR
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_TITLE_SIZE

abstract class AskBuilder<T : AskBuilder<T>>(protected val context: Context) {
    protected val title: TextConfig = TextConfig(null, ContextCompat.getColor(context, DEFAULT_TITLE_COLOR), true, DEFAULT_TITLE_SIZE, DimensionEnum.SP)
    protected val message: TextConfig = TextConfig(null, ContextCompat.getColor(context, R.color.colorDialog6), true, DEFAULT_MESSAGE_SIZE, DimensionEnum.SP)
    protected val buttonList = mutableListOf<TextConfig>()
    protected var clickListener: AskItemClickListener? = null
    protected var dimAmount = 0.4f
    protected var canceledOnTouchOutside = true
    protected var cancelable = true
    protected abstract val MAX_BUTTON_COUNT: Int

    abstract fun self(): T

    @CheckResult
    fun setTitle(title: CharSequence, @ColorInt textColor: Int = ContextCompat.getColor(context, DEFAULT_TITLE_COLOR), textSize: Float = DEFAULT_TITLE_SIZE, dimensionEnum: DimensionEnum = DimensionEnum.SP, isBold: Boolean = true): T {
        this.title.textColor = textColor
        this.title.text = title
        this.title.textSize = textSize
        this.title.dimensionEnum = dimensionEnum
        this.title.isBold = isBold
        return self()
    }

    @CheckResult
    fun setMessage(message: CharSequence, @ColorInt textColor: Int = ContextCompat.getColor(context, DEFAULT_MESSAGE_COLOR), textSize: Float = DEFAULT_MESSAGE_SIZE, dimensionEnum: DimensionEnum = DimensionEnum.SP, isBold: Boolean = false): T {
        this.message.textColor = textColor
        this.message.text = message
        this.message.textSize = textSize
        this.message.dimensionEnum = dimensionEnum
        this.message.isBold = isBold
        return self()
    }

    protected fun addButton(textConfig: TextConfig) {
        if (buttonList.size < MAX_BUTTON_COUNT) {
            buttonList.add(textConfig)
        } else {
            throw Throwable("按钮不能超过${MAX_BUTTON_COUNT}个")
        }
    }

    @CheckResult
    fun addCancelButton(button: CharSequence): T {
        val textConfig = TextConfig(button, ContextCompat.getColor(context, R.color.colorDialogButtonCancel), true, DEFAULT_BUTTON_SIZE, DimensionEnum.SP)
        addButton(textConfig)
        return self()
    }

    @CheckResult
    fun addHighLightButton(button: CharSequence): T {
        val textConfig = TextConfig(button, ContextCompat.getColor(context, R.color.colorDialogButtonHighlight), false, DEFAULT_BUTTON_SIZE, DimensionEnum.SP)
        addButton(textConfig)
        return self()
    }

    @CheckResult
    fun addNormalButton(button: CharSequence): T {
        val textConfig = TextConfig(button, ContextCompat.getColor(context, R.color.colorDialogButtonNormal), false, DEFAULT_BUTTON_SIZE, DimensionEnum.SP)
        addButton(textConfig)
        return self()
    }

    @CheckResult
    fun addOtherButton(button: CharSequence, @ColorInt textColor: Int, textSize: Float, dimensionEnum: DimensionEnum, isBold: Boolean): T {
        val textConfig = TextConfig(button, textColor, isBold, textSize, dimensionEnum)
        addButton(textConfig)
        return self()
    }

    @CheckResult
    fun setItemClickListener(listener: (index: Int, text: TextConfig) -> Unit): T {
        this.clickListener = object : AskItemClickListener() {
            override fun onClick(index: Int, text: TextConfig) {
                listener(index, text)
            }
        }
        return self()
    }

    @CheckResult
    fun setItemClickListener(listener: AskItemClickListener): T {
        this.clickListener = listener
        return self()
    }

    @CheckResult
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0, fromInclusive = true, toInclusive = true) dimAmount: Float): T {
        this.dimAmount = dimAmount
        return self()
    }

    @CheckResult
    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): T {
        this.canceledOnTouchOutside = canceledOnTouchOutside
        return self()
    }

    @CheckResult
    fun setCancelable(cancelable: Boolean): T {
        this.cancelable = cancelable
        return self()
    }

    @CheckResult
    abstract fun build(): AskHolder
}