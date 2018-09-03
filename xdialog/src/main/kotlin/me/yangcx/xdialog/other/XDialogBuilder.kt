package me.yangcx.xdialog.other

import android.content.Context
import android.support.annotation.CheckResult
import android.support.annotation.FloatRange
import android.support.annotation.LayoutRes
import android.view.Gravity
import android.view.View
import me.yangcx.xdialog.base.BaseDialogFragment
import me.yangcx.xdialog.callback.OnDismissedListener
import me.yangcx.xdialog.callback.OnShowInAnimationListener
import me.yangcx.xdialog.callback.OnShowListener
import me.yangcx.xdialog.callback.OnShowOutAnimationListener
import me.yangcx.xdialog.entity.DimensionConfig
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_SHOW_TAG

class XDialogBuilder constructor(@LayoutRes private val layoutResId: Int) {
    private var width = DimensionConfig(0f, DimensionEnum.DP, false, true)
    private var height = DimensionConfig(0f, DimensionEnum.DP, false, true)
    private var dimAmount = 0f
    private var showTag = DEFAULT_SHOW_TAG
    private var canceledOnTouchOutside = true
    private var cancelable = true
    private var gravity = Gravity.CENTER
    private var onShowInAnimationListener: OnShowInAnimationListener? = null
    private var onShowOutAnimationListener: OnShowOutAnimationListener? = null
    private val onShowListenerList = mutableListOf<OnShowListener>()
    private val onDismissedListenerList = mutableListOf<OnDismissedListener>()

    @CheckResult
    fun setWidth(width: Float, dimensionEnum: DimensionEnum): XDialogBuilder {
        this.width.dimensionEnum = dimensionEnum
        this.width.isPercent = false
        this.width.isWrap = false
        this.width.size = width
        return this
    }

    @CheckResult
    fun setWidthPercent(@FloatRange(from = 0.0, to = 1.0, fromInclusive = true, toInclusive = true) widthPercent: Float): XDialogBuilder {
        this.width.isPercent = true
        this.width.isWrap = false
        this.width.size = widthPercent
        return this
    }

    @CheckResult
    fun setWidthWrap(): XDialogBuilder {
        this.width.isPercent = false
        this.width.isWrap = true
        this.width.size = 0f
        return this
    }

    @CheckResult
    fun setHeight(height: Float, dimensionEnum: DimensionEnum): XDialogBuilder {
        this.height.size = height
        this.height.dimensionEnum = dimensionEnum
        this.height.isPercent = false
        this.height.isWrap = false
        return this
    }

    @CheckResult
    fun setHeightPercent(@FloatRange(from = 0.0, to = 1.0, fromInclusive = true, toInclusive = true) heightPercent: Float): XDialogBuilder {
        this.height.size = heightPercent
        this.height.isPercent = true
        this.height.isWrap = false
        return this
    }

    @CheckResult
    fun setHeightWrap(): XDialogBuilder {
        this.height.size = 0f
        this.height.isPercent = false
        this.height.isWrap = true
        return this
    }

    @CheckResult
    fun setDimAmount(@FloatRange(from = 0.0, to = 1.0, fromInclusive = true, toInclusive = true) dimAmount: Float): XDialogBuilder {
        this.dimAmount = dimAmount
        return this
    }


    @CheckResult
    fun setShowTag(showTag: String): XDialogBuilder {
        this.showTag = showTag
        return this
    }

    @CheckResult
    fun setCanceledOnTouchOutside(canceledOnTouchOutside: Boolean): XDialogBuilder {
        this.canceledOnTouchOutside = canceledOnTouchOutside
        return this
    }

    @CheckResult
    fun setCancelable(cancelable: Boolean): XDialogBuilder {
        this.cancelable = cancelable
        return this
    }

    @CheckResult
    fun setGravity(gravity: Int): XDialogBuilder {
        this.gravity = gravity
        return this
    }

    @CheckResult
    fun setInAnimation(onInAnimation: (view: View) -> Unit): XDialogBuilder {
        this.onShowInAnimationListener = object : OnShowInAnimationListener() {
            override fun onShowAnimation(view: View) {
                onInAnimation(view)
            }
        }
        return this
    }

    @CheckResult
    fun setOutAnimation(onOutAnimation: (view: View, holder: XDismissHolder) -> Unit): XDialogBuilder {
        this.onShowOutAnimationListener = object : OnShowOutAnimationListener() {
            override fun onShowAnimation(view: View, holder: XDismissHolder) {
                onOutAnimation(view, holder)
            }
        }
        return this
    }

    @CheckResult
    fun addOnShowListener(onShow: (view: View, holder: XDialogHolder) -> Unit): XDialogBuilder {
        onShowListenerList.add(object : OnShowListener() {
            override fun onShow(view: View, holder: XDialogHolder) {
                onShow(view, holder)
            }
        })
        return this
    }

    @CheckResult
    fun addOnDismissedListener(onDismissed: () -> Unit): XDialogBuilder {
        onDismissedListenerList.add(object : OnDismissedListener() {
            override fun onDismissed() {
                onDismissed()
            }
        })
        return this
    }

    @CheckResult
    fun build(): XDialogHolder {
        return BaseDialogFragment.getInstance(buildConfig())
    }

    @CheckResult
    internal fun buildConfig(): XDialogConfig {
        val config = XDialogConfig(layoutResId, width, height, dimAmount, canceledOnTouchOutside, cancelable, gravity, onShowInAnimationListener, onShowOutAnimationListener, showTag)
        config.onShowListenerList.addAll(onShowListenerList)
        config.onDismissedListenerList.addAll(onDismissedListenerList)
        return config
    }
}