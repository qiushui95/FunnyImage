package me.yangcx.funnyimage.weight

import android.content.Context
import android.graphics.*
import android.os.Handler
import android.os.Looper
import android.util.AttributeSet
import android.view.View
import timber.log.Timber
import kotlin.math.min

class FunnyImageView(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : View(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context) : this(context, null)

    private val handlerMap by lazy { mutableMapOf<Int, Handler>() }

    private val paint by lazy {
        Paint()
    }

    private var image: Bitmap? = null

    private val imageWidth
        get() = image?.width ?: 0

    private val imageHeight
        get() = image?.height ?: 0

    private var isHorizontal = true
    private var isStartToEnd = true

    private val src by lazy {
        Rect()
    }
    private val dst by lazy {
        Rect(0, 0, width, height)
    }

    fun setImage(image: Bitmap) {
        val realImage = getRealImage(image)
        val widthRatio = realImage.width * 1f / width
        val heightRatio = realImage.height * 1f / height
        isHorizontal = widthRatio > heightRatio
        isStartToEnd = true
        this.image = realImage
        resume()
    }

    private fun getRealImage(image: Bitmap): Bitmap {
        val ratio = min(image.width * 1f / width, image.height * 1f / height)
        val realWidth = (image.width * ratio).toInt()
        val realHeight = (image.height * ratio).toInt()
        val realImage = Bitmap.createBitmap(realWidth, realHeight, Bitmap.Config.RGB_565)
        val canvas = Canvas(realImage)
        val src = Rect(0, 0, image.width, image.height)
        canvas.drawBitmap(image, src, src, paint)
        this.src.set(0, 0, (width * ratio).toInt(), (height * ratio).toInt())
        return realImage
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        image?.let {
            Timber.e(src.toString())
            canvas.drawBitmap(it, src, dst, paint)
            val handler = handlerMap[it.hashCode()]
            if (handler != null) {
                doNext()
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
        invalidate()
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

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        image?.recycle()
    }
}