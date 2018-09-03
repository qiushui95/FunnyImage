package me.yangcx.xdialog.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import me.yangcx.xdialog.R
import kotlin.math.min

class ProgressView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    private val DEFAULT_WIDTH = 50
    private val DEFAULT_BORDER_COLOR = Color.parseColor("#FF000000")
    private val DEFAULT_COMPLETE_COLOR = Color.parseColor("#FF000000")

    private val paint by lazy { Paint() }

    private val borderColor: Int
    private val completeColor: Int
    private var progress = 26f
    private val rectF by lazy {
        RectF(paint.strokeWidth / 2f, paint.strokeWidth / 2f, width - paint.strokeWidth / 2f, height - paint.strokeWidth / 2f)
    }

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    init {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.ProgressView)
        borderColor = attributes.getColor(R.styleable.ProgressView_progressBorderColor, DEFAULT_BORDER_COLOR)
        completeColor = attributes.getColor(R.styleable.ProgressView_progressCompleteColor, DEFAULT_COMPLETE_COLOR)
        paint.isAntiAlias = true
        paint.strokeWidth = attributes.getDimension(R.styleable.ProgressView_progressBorderSize, 5f)
        attributes.recycle()
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
        paint.style = Paint.Style.STROKE
        paint.color = borderColor
        canvas.drawCircle(rectF.centerX(), rectF.centerY(), rectF.width() / 2, paint)
        paint.style = Paint.Style.FILL
        paint.color = completeColor
        canvas.drawArc(rectF, -90f, min(1f, progress) * 360, true, paint)
    }

    fun updateProgress(progress: Float) {
        this.progress=progress
        postInvalidate()
    }
}