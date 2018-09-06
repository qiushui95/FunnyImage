package me.yangcx.funnyimage.glide

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.Priority
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions

@GlideModule
class FunnyGlideModule : AppGlideModule() {
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        super.applyOptions(context, builder)
        builder.setLogLevel(Log.DEBUG)
                .setDefaultRequestOptions(
                        RequestOptions()
                                .encodeFormat(Bitmap.CompressFormat.WEBP)
                                .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
                )
    }
}