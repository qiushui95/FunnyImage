package me.yangcx.xdialog.ask.sheet

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.xdialog_sheet.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.ask.common.AskHolder
import me.yangcx.xdialog.ask.sheet.SheetBuilder.Companion.CANCEL_INDEX
import me.yangcx.xdialog.base.BaseDialogFragment
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.other.XDialogConfig
import me.yangcx.xdialog.utils.DimensionUtils

internal class SheetDialog : BaseDialogFragment(), AskHolder {
    private val config by lazy { arguments?.getParcelable<SheetConfig>(KEY_SHEET_CONFIG) }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTitle(view, config?.title)
        createMessage(view, config?.message)
        createButtons(view, config?.buttonList)
        configCancel(view, config?.cancel)
    }

    private fun createTextView(text: CharSequence, textConfig: TextConfig): TextView {
        val textView = TextView(activity)
        textView.gravity = Gravity.CENTER
        textView.setTextColor(textConfig.textColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, DimensionUtils.toPxFloat( textConfig.textSize, textConfig.dimensionEnum))
        textView.typeface = if (textConfig.isBold) {
            Typeface.DEFAULT_BOLD
        } else {
            Typeface.DEFAULT
        }
        textView.text = text
        return textView
    }

    private fun configTopTextView(textView: TextView) {
        textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.setPadding(
                DimensionUtils.toPxInt( 10f, DimensionEnum.DP),
                DimensionUtils.toPxInt( 5f, DimensionEnum.DP),
                DimensionUtils.toPxInt( 10f, DimensionEnum.DP),
                DimensionUtils.toPxInt( 5f, DimensionEnum.DP)
        )
    }

    private fun createMessage(parent: View, messageConfig: TextConfig?) {
        val messageText = messageConfig?.text
        if (messageText != null) {
            val textView = createTextView(messageText, messageConfig)
            configTopTextView(textView)
            parent.llTop.addView(textView)
        }
    }

    private fun createTitle(parent: View, titleConfig: TextConfig?) {
        val titleText = titleConfig?.text
        if (titleText != null) {
            val textView = createTextView(titleText, titleConfig)
            configTopTextView(textView)
            parent.llTop.addView(textView)
        }
    }

    private fun createButtons(view: View, buttonList: MutableList<TextConfig>?) {
        buttonList?.withIndex()?.forEach {
            createButton(view, it.value, it.index == buttonList.size - 1)
        }
    }

    private fun createButton(parent: View, textConfig: TextConfig, isLast: Boolean) {
        val buttonText = textConfig.text
        if (buttonText != null) {
            createHorizontalDivider(parent)
            val textView = createTextView(buttonText, textConfig)
            configBottomTextView(parent, textView, isLast)
            parent.llTop.addView(textView)
        }
    }

    private fun createHorizontalDivider(parent: View) {
        if (parent.llTop.childCount > 0) {
            val divider = View(activity)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
            divider.layoutParams = layoutParams
            divider.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorDialogAlertDivider))
            parent.llTop.addView(divider)
        }
    }

    private fun configBottomTextView(parent: View, textView: TextView, isLast: Boolean) {
        textView.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        textView.setPadding(
                0,
                resources.getDimensionPixelOffset(R.dimen.dimenDialogButtonPadding),
                0,
                resources.getDimensionPixelOffset(R.dimen.dimenDialogButtonPadding)
        )
        textView.setBackgroundResource(if (!isLast) {
            R.drawable.bg_alert_button_middle
        } else {
            R.drawable.bg_alert_button_just_one
        })
        textView.setOnClickListener { clickView ->
            val index = 0.until(parent.llTop.childCount)
                    .map {
                        parent.llTop.getChildAt(it)
                    }.filter {
                        it is TextView
                    }.indexOf(clickView)
            if (index in 0.until(config?.buttonList?.size ?: 0)) {
                val textConfig = config?.buttonList?.get(index)
                if (textConfig != null) {
                    addDismissedListener {
                        config?.clickListener?.onClick(index, textConfig)
                    }
                }
            }
            dismiss()
        }
    }

    private fun configCancel(view: View, cancel: TextConfig?) {
        val cancelText = cancel?.text
        if (cancelText != null) {
            view.tvCancel.text = cancelText
            view.tvCancel.typeface = if (cancel.isBold) {
                Typeface.DEFAULT_BOLD
            } else {
                Typeface.DEFAULT
            }
            view.tvCancel.setTextColor(cancel.textColor)
            view.tvCancel.setOnClickListener {
                addDismissedListener {
                    val listener = config?.clickListener
                    val cancelConfig = config?.cancel
                    if (listener != null && cancelConfig != null) {
                        listener.onClick(CANCEL_INDEX, cancelConfig)
                    }
                }
                dismiss()
            }
        } else {
            view.tvCancel.visibility = View.GONE
        }
    }

    companion object {
        private const val KEY_SHEET_CONFIG = "sheetConfig"
        fun getInstance(sheetConfig: SheetConfig, baseConfig: XDialogConfig): AskHolder {
            val instance = SheetDialog()
            val bundle = Bundle()
            bundle.putParcelable(KEY_SHEET_CONFIG, sheetConfig)
            bundle.putParcelable(KEY_NORMAL_CONFIG, baseConfig)
            instance.arguments = bundle
            return instance
        }
    }
}