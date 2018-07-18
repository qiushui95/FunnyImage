package com.example.xdiolog.fragment

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.*
import android.view.inputmethod.InputMethodManager
import com.example.xdiolog.fragment.listener.DismissedListener
import com.example.xdiolog.fragment.listener.OnShowListener
import com.example.xdiolog.fragment.listener.PreDismissListener
import java.util.*

abstract class XDialogFragment(@LayoutRes private val layoutResId: Int) : DialogFragment(), XDialogFragmentBuilder, DismissAction {
    var width = WindowManager.LayoutParams.WRAP_CONTENT
        private set
    private var widthDpValue = false
    var height = WindowManager.LayoutParams.WRAP_CONTENT
        private set
    private var heightDpValue = false
    var backColor = Color.TRANSPARENT
        private set
    var showTag = UUID.randomUUID().toString().toUpperCase()
        private set
    var outsideTouchable = true
        private set
    var backPressable = true
        private set
    private val onShowListenerList by lazy { mutableListOf<OnShowListener>() }
    private val preDismissListenerList by lazy { mutableListOf<PreDismissListener>() }
    private val dismissedListenerList by lazy { mutableListOf<DismissedListener>() }

    override fun setWidth(width: Int, isDp: Boolean): XDialogFragmentBuilder {
        this.width = width
        this.widthDpValue = isDp
        return this
    }

    override fun setHeight(height: Int, isDp: Boolean): XDialogFragmentBuilder {
        this.height = height
        this.heightDpValue = isDp
        return this
    }


    override fun setBackColor(backColor: Int): XDialogFragmentBuilder {
        this.backColor = backColor
        return this
    }


    override fun setOutsideTouchable(outsideTouchable: Boolean): XDialogFragmentBuilder {
        this.outsideTouchable = outsideTouchable
        return this
    }


    override fun setBackPressable(backPressable: Boolean): XDialogFragmentBuilder {
        this.backPressable = backPressable
        return this
    }

    override fun addShowListener(listener: OnShowListener): XDialogFragmentBuilder {
        onShowListenerList.add(listener)
        return this
    }

    override fun addPreDismissListener(listener: PreDismissListener): XDialogFragmentBuilder {
        preDismissListenerList.add(listener)
        return this
    }

    override fun addDismissedListener(listener: DismissedListener): XDialogFragmentBuilder {
        dismissedListenerList.add(listener)
        return this
    }

    override fun showImmediately(manager: FragmentManager) {
        super.show(manager, showTag)

    }

    override fun showImmediately(manager: FragmentManager, showTag: String) {
        this.showTag = showTag
        super.show(manager, showTag)
    }

    @Deprecated(message = "please use showImmediately", replaceWith = ReplaceWith("showImmediately"), level = DeprecationLevel.ERROR)
    override fun show(manager: FragmentManager, showTag: String) {

    }

    /**
     * 初始化界面
     */
    abstract fun initThis(view: View)


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.setCancelable(false)
        return inflater.inflate(layoutResId, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initThis(view)
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return XDialog(requireContext(), theme, onShowListenerList, preDismissListenerList, dismissedListenerList)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        super.onActivityCreated(savedInstanceState)
        initWidth()
        initHeight()
        initBackColor()
        initOutsideTouchable()
        initBackPressable()
    }

    override fun dismissImmediately() {
        super.dismiss()
    }

    override fun dismiss() {
        val parent = dialog?.window?.decorView
        if (parent is View) {
            var dismissImmediately = true
            preDismissListenerList.toList()
                    .forEach {
                        if (it.preDismiss(parent, this)) {
                            dismissImmediately = false
                        }
                        preDismissListenerList.remove(it)
                    }
            if (dismissImmediately) {
                dismissImmediately()
            }
        }
        if (parent !is View) {
            dismissImmediately()
        }
    }

    private fun dp2Px(dpValue: Int): Int {
        return (requireContext().resources.displayMetrics.density * dpValue + 0.5f).toInt()
    }

    private fun initWidth() {
        dialog?.window?.apply {
            val params = attributes
            params.width = if (widthDpValue) {
                dp2Px(this@XDialogFragment.width)
            } else {
                this@XDialogFragment.width
            }
            attributes = params
        }
    }

    private fun initHeight() {
        dialog?.window?.apply {
            val params = attributes
            params.height = if (this@XDialogFragment.heightDpValue) {
                dp2Px(this@XDialogFragment.height)
            } else {
                this@XDialogFragment.height
            }
            attributes = params
        }
    }

    private fun initBackColor() {
        dialog?.window?.apply {
            setBackgroundDrawable(ColorDrawable(this@XDialogFragment.backColor))
        }
    }

    private fun initOutsideTouchable() {
        dialog?.apply {
            setCanceledOnTouchOutside(this@XDialogFragment.outsideTouchable)
        }
    }

    private fun initBackPressable() {
        dialog?.apply {
            if (!this@XDialogFragment.backPressable) {
                setOnKeyListener(null)
            } else {
                setOnKeyListener { _, keyCode, event ->
                    if (keyCode == KeyEvent.KEYCODE_BACK && event.action == MotionEvent.ACTION_DOWN && event.repeatCount == 0) {
                        dismiss()
                        true
                    } else {
                        false
                    }
                }
            }
        }
    }

    /**
     * 隐藏软键盘
     */
    protected fun hideSoftInput() {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(activity?.window?.currentFocus?.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    protected fun hideSoftInput(target: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.hideSoftInputFromWindow(target.windowToken, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }

    /**
     * 显示软键盘
     */
    fun showSoftInput(view: View) {
        val imm = activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager?
        imm?.showSoftInput(view, InputMethodManager.RESULT_UNCHANGED_SHOWN)
    }
}