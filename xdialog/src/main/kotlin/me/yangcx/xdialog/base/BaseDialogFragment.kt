package me.yangcx.xdialog.base

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.CheckResult
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.DisplayMetrics
import android.view.*
import android.view.inputmethod.InputMethodManager
import me.yangcx.xdialog.callback.OnDismissedListener
import me.yangcx.xdialog.other.XDialogConfig
import me.yangcx.xdialog.other.XDialogHolder
import me.yangcx.xdialog.other.XDismissHolder
import me.yangcx.xdialog.other.XDismissMoreHolder
import me.yangcx.xdialog.utils.DimensionUtils
import kotlin.math.roundToInt

internal open class BaseDialogFragment : DialogFragment(), XDialogHolder, XDismissMoreHolder{
    private val config by lazy { arguments?.getParcelable<XDialogConfig>(KEY_NORMAL_CONFIG) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (config == null) {
            throw RuntimeException("arguments未发现config配置,请检查是否传输")
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setCancelable(false)
        return inflater.inflate(config!!.layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.setOnShowListener { _ ->
            config?.onShowListenerList
                    ?.forEach {
                        it.onShow(view, this)
                    }
            config?.onShowInAnimationListener?.onShowAnimation(view)
            config?.onShowInAnimationListener = null
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return BaseDialog(requireContext(), theme, this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        super.onActivityCreated(savedInstanceState)
        initConfig()
    }

    private fun initConfig() {
        val config = config
        if (config != null) {
            val window = dialog?.window
            val layoutParams = window?.attributes
            if (layoutParams != null) {
                layoutParams.width = if (config.width.isWrap) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else if (config.width.isPercent && config.width.size >= 0f && config.width.size <= 1f) {
                    (getScreenWidth() * config.width.size).roundToInt()
                } else {
                    DimensionUtils.toPxInt( config.width.size, config.width.dimensionEnum)
                }
                layoutParams.height = if (config.height.isWrap) {
                    ViewGroup.LayoutParams.WRAP_CONTENT
                } else if (config.height.isPercent && config.height.size >= 0f && config.height.size <= 1f) {
                    (getScreenHeight() * config.height.size).roundToInt()
                } else {
                    DimensionUtils.toPxInt( config.height.size, config.height.dimensionEnum)
                }
                layoutParams.gravity = config.gravity
                layoutParams.dimAmount = config.dimAmount
                dialog?.window?.attributes = layoutParams
            }
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            dialog?.setCanceledOnTouchOutside(config.canceledOnTouchOutside)
            dialog?.setCancelable(config.cancelable)
        }
    }


    override fun onDetach() {
        super.onDetach()
        config?.onDismissedListenerList
                ?.forEach {
                    it.onDismissed()
                }
    }

    override fun dismiss() {
        val outAnimationListener = config?.onShowOutAnimationListener
        val decorView = dialog?.window?.decorView
        dialog?.setCancelable(false)
        dialog?.setCanceledOnTouchOutside(false)
        dismissView(decorView)
        if (outAnimationListener != null && decorView != null) {
            outAnimationListener.onShowAnimation(decorView, this)
        } else {
            super.dismiss()
        }
    }

    private fun dismissView(view: View?) {
        view?.apply {
            if (this is ViewGroup) {
                0.until(childCount)
                        .map {
                            getChildAt(it)
                        }.forEach {
                            dismissView(it)
                        }
            } else {
                isClickable = false
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        arguments?.putParcelable(KEY_NORMAL_CONFIG, config)
        super.onSaveInstanceState(outState)
    }

    fun getShowTag() = config?.showTag ?: "showTag"

    override fun show(manager: FragmentManager) {
        if (dialog?.isShowing != true) {
            super.show(manager, getShowTag())
        }
    }


    override fun dismissWithoutAnimation() {
        if (dialog?.isShowing == true) {
            super.dismiss()
        }
    }

    protected fun addDismissedListener(onDismissed: () -> Unit) {
        config?.onDismissedListenerList?.add(object : OnDismissedListener() {
            override fun onDismissed() {
                onDismissed()
            }
        })
    }

    override fun dismissWithoutAnimation(onDismissed: () -> Unit) {
        if (dialog?.isShowing == true) {
            config?.onDismissedListenerList
                    ?.add(object : OnDismissedListener() {
                        override fun onDismissed() {
                            onDismissed()
                        }
                    })
        }
        dismissWithoutAnimation()
    }

    override fun dismissNow() {
        if (dialog?.isShowing == true) {
            dismiss()
        }
    }

    override fun dismissNow(onDismissed: () -> Unit) {
        if (dialog?.isShowing == true) {
            config?.onDismissedListenerList
                    ?.add(object : OnDismissedListener() {
                        override fun onDismissed() {
                            onDismissed()
                        }
                    })
        }
        dismissNow()
    }

    override fun hideSoftInput() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(activity?.window?.currentFocus?.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    override fun hideSoftInput(target: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(target.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    override fun showSoftInput(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    @CheckResult
    private fun getScreenWidth(): Int {
        val service = requireContext().getSystemService(Context.WINDOW_SERVICE)
        return if (service is WindowManager) {
            val outMetrics = DisplayMetrics()
            service.defaultDisplay.getMetrics(outMetrics)
            outMetrics.widthPixels
        } else {
            0
        }
    }

    @CheckResult
    private fun getScreenHeight(): Int {
        val service = requireContext().getSystemService(Context.WINDOW_SERVICE)
        return if (service is WindowManager) {
            val outMetrics = DisplayMetrics()
            service.defaultDisplay.getMetrics(outMetrics)
            outMetrics.heightPixels
        } else {
            0
        }
    }

    companion object {
        internal const val KEY_NORMAL_CONFIG = "dialogFragmentNormalConfig"
        internal fun getInstance(config: XDialogConfig): XDialogHolder {
            val instance = BaseDialogFragment()
            val bundle = Bundle()
            bundle.putParcelable(KEY_NORMAL_CONFIG, config)
            instance.arguments = bundle
            return instance
        }

    }
}