package me.yangcx.funnyimage.weight

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.widget.ImageView

class FunnyImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ImageView(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)


    private val handlerMap by lazy { mutableMapOf<Int, Handler>() }

    private val paint by lazy { Paint() }

    private var image: Drawable? = null

    private val imageWidth
        get() = image?.intrinsicWidth ?: 0
    private val imageHeight
        get() = image?.intrinsicHeight ?: 0

    private var isHorizontal = true
    private var isStartToEnd = true

    private val src by lazy {
        Rect()
    }
    private val dst by lazy {
        Rect(0, 0, width, height)
    }

    override fun setImageDrawable(drawable: Drawable?) {
        image = drawable
        drawable?.also {
            isHorizontal = imageWidth > width
            isStartToEnd = true
            src.set(0, 0, width, height)
            resume()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        image?.also {
            if (it is BitmapDrawable) {
                canvas.drawBitmap(it.bitmap, src, dst, paint)
                val handler = handlerMap[it.hashCode()]
                if (handler != null) {
                    doNext()
                }
            }
        }
    }

    private fun doNext() {
        if (isHorizontal) {
            if (src.right + 1 > imageWidth && isStartToEnd) {
                isStartToEnd = false
            } else if (src.left - 1 < 0 && !isStartToEnd) {
                isStartToEnd = true
            } else if (isStartToEnd) {
                src.right += 1
                src.left += 1
            } else {
                src.right -= 1
                src.left -= 1
            }
        } else {
            if (src.bottom + 1 > imageHeight && isStartToEnd) {
                isStartToEnd = false
            } else if (src.top - 1 < 0 && !isStartToEnd) {
                isStartToEnd = true
            } else if (isStartToEnd) {
                src.top += 1
                src.bottom += 1
            } else {
                src.top -= 1
                src.bottom -= 1
            }
        }
        if (imageWidth - width > 10 || imageHeight - height > 10) {
            invalidate()
        }
    }


    fun resume() {
        val image = image
        if (image != null && handlerMap[image.hashCode()] == null) {
            handlerMap[image.hashCode()] = Handler(Looper.getMainLooper())
            invalidate()
        }
    }

    fun pause() {
        val handler = handlerMap[this.image?.hashCode()]
        handlerMap.remove(this.image?.hashCode())
        handler?.removeCallbacksAndMessages(null)
    }
}