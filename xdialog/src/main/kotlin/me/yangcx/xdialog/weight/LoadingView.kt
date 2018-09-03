package me.yangcx.xdialog.weight

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateInterpolator
import android.view.animation.AnimationUtils
import android.view.animation.DecelerateInterpolator
import me.yangcx.xdialog.R
import kotlin.math.min

class LoadingView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    private val rotateAnimation by lazy { AnimationUtils.loadAnimation(context, R.anim.anim_loading_rotate) }
    private val DEFAULT_WIDTH = 50
    private val DEFAULT_SATRT_COLOR = Color.parseColor("#FF000000")
    private val DEFAULT_END_COLOR = Color.parseColor("#00000000")
    private val DEFAULT_STROKE_WIDTH = 10

    private var colorStart: Int = DEFAULT_SATRT_COLOR
    private var colorEnd: Int = DEFAULT_END_COLOR

    private val paint = Paint()
    private val rectF by lazy {
        RectF(0f, 0f, width * 1f, height * 1f)
    }
    private val shader by lazy {
        SweepGradient(rectF.width() / 2f, rectF.height() / 2f, colorStart, colorEnd)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        paint.isAntiAlias = true
        paint.style = Paint.Style.STROKE
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingView)
        paint.strokeWidth = typedArray.getDimensionPixelOffset(R.styleable.LoadingView_loadingStrokeWidth, DEFAULT_STROKE_WIDTH) * 1f
        colorStart = typedArray.getColor(R.styleable.LoadingView_loadingStartColor, DEFAULT_SATRT_COLOR)
        colorEnd = typedArray.getColor(R.styleable.LoadingView_loadingEndColor, DEFAULT_END_COLOR)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = measure(widthMeasureSpec)
        val height = measure(heightMeasureSpec)
        val size = min(width, height)
        setMeasuredDimension(size, size)
    }

    private fun measure(origin: Int): Int {
        val specMode = View.MeasureSpec.getMode(origin)
        val specSize = View.MeasureSpec.getSize(origin)
        return if (specMode == MeasureSpec.EXACTLY) {
            specSize
        } else if (specMode == MeasureSpec.AT_MOST) {
            Math.min(DEFAULT_WIDTH, specSize)
        } else {
            DEFAULT_WIDTH
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        rectF.left = paint.strokeWidth / 2f
        rectF.right = width * 1f - paint.strokeWidth / 2f
        rectF.top = paint.strokeWidth / 2f
        rectF.bottom = height * 1f - paint.strokeWidth / 2f
        paint.shader = shader
        canvas.drawArc(rectF, 0f, 360f, false, paint)
    }

    fun startAnimation() {
        startAnimation(rotateAnimation)
    }

    fun stoptAnimation() {
        clearAnimation()
    }
}