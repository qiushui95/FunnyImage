package me.yangcx.xdialog.ask.alert

import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import kotlinx.android.synthetic.main.xdialog_alert.view.*
import me.yangcx.xdialog.R
import me.yangcx.xdialog.ask.common.AskHolder
import me.yangcx.xdialog.base.BaseDialogFragment
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.entity.TextConfig
import me.yangcx.xdialog.other.XDialogConfig
import me.yangcx.xdialog.utils.DimensionUtils

internal class AlertDialog : BaseDialogFragment(), AskHolder {
    private val config by lazy { arguments?.getParcelable<AlertConfig>(KEY_ALERT_CONFIG) }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createTitle(view, config?.title)
        createMessage(view, config?.message)
        createButtons(view, config?.buttonList)
    }

    private fun createTextView(text: CharSequence, textConfig: TextConfig): TextView {
        val textView = TextView(activity)
        textView.gravity = Gravity.CENTER
        textView.setTextColor(textConfig.textColor)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_PX,DimensionUtils.toPxFloat(textConfig.textSize, textConfig.dimensionEnum))
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
                DimensionUtils.toPxInt(10f, DimensionEnum.DP),
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
            createButton(view, it.value, it.index == 0, it.index == buttonList.size - 1)
        }
        createHorizontalDivider(view)
    }

    private fun createButton(parent: View, textConfig: TextConfig, isFirst: Boolean, isLast: Boolean) {
        val buttonText = textConfig.text
        if (buttonText != null) {
            createVerticalDivider(parent)
            val textView = createTextView(buttonText, textConfig)
            configBottomTextView(parent, textView, isFirst, isLast)
            parent.llBottom.addView(textView)
        }
    }

    private fun createHorizontalDivider(parent: View) {
        if (parent.llBottom.childCount > 0) {
            val divider = View(activity)
            val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 1)
            layoutParams.topMargin = DimensionUtils.toPxInt( 10f, DimensionEnum.DP)
            divider.layoutParams = layoutParams
            divider.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorDialogAlertDivider))
            parent.llAlert.addView(divider, 1)
        }
    }

    private fun createVerticalDivider(parent: View) {
        if (parent.llBottom.childCount > 0) {
            val divider = View(activity)
            divider.layoutParams = LinearLayout.LayoutParams(1, LinearLayout.LayoutParams.MATCH_PARENT)
            divider.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.colorDialogAlertDivider))
            parent.llBottom.addView(divider)
        }
    }

    private fun configBottomTextView(parent: View, textView: TextView, isFirst: Boolean, isLast: Boolean) {
        textView.layoutParams = LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.WRAP_CONTENT, 1f)
        textView.setPadding(
                0,
                resources.getDimensionPixelOffset(R.dimen.dimenDialogButtonPadding),
                0,
                resources.getDimensionPixelOffset(R.dimen.dimenDialogButtonPadding)
        )
        textView.setBackgroundResource(if (isFirst && isLast) {
            R.drawable.bg_alert_button_just_one
        } else if (isFirst) {
            R.drawable.bg_alert_button_left
        } else if (isLast) {
            R.drawable.bg_alert_button_right
        } else {
            R.drawable.bg_alert_button_middle
        })
        textView.setOnClickListener { clickView ->
            val index = 0.until(parent.llBottom.childCount)
                    .map {
                        parent.llBottom.getChildAt(it)
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

    companion object {
        private const val KEY_ALERT_CONFIG = "alertConfig"
        internal fun getInstance(alertConfig: AlertConfig, baseConfig: XDialogConfig): AskHolder {
            val instance = AlertDialog()
            val bundle = Bundle()
            bundle.putParcelable(KEY_ALERT_CONFIG, alertConfig)
            bundle.putParcelable(KEY_NORMAL_CONFIG, baseConfig)
            instance.arguments = bundle
            return instance
        }
    }
}