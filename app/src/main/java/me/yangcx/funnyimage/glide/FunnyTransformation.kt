package me.yangcx.funnyimage.glide

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation
import java.security.MessageDigest
import kotlin.math.min

class FunnyTransformation : BitmapTransformation() {
    override fun transform(pool: BitmapPool, resource: Bitmap, outWidth: Int, outHeight: Int): Bitmap {
        val imageWidth = resource.width
        val imageHeight = resource.height
        val ratio = min(imageWidth * 1f / outWidth, imageHeight * 1f / outHeight)
        val realWidth = (imageWidth / ratio).toInt()
        val realHeight = (imageHeight / ratio).toInt()
        val result = Bitmap.createBitmap(realWidth, realHeight, Bitmap.Config.RGB_565)
        val canvas = Canvas(result)
        val src = Rect(0, 0, imageWidth, imageHeight)
        val dst = Rect(0, 0, realWidth, realHeight)
        canvas.drawBitmap(resource, src, dst, Paint())
        return result
    }

    override fun updateDiskCacheKey(messageDigest: MessageDigest) {

    }
}