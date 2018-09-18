package me.yangcx.ximagewatcher.utils

import android.content.Context
import android.content.Intent
import android.os.Parcelable
import me.yangcx.ximagewatcher.loader.ImageWatcherLoader
import me.yangcx.ximagewatcher.ui.ImageWatcherActivity

object ImageWatcher {
    const val KEY_IMAGEWATCHER_IMAGES = "images"
    lateinit var loader: ImageWatcherLoader
    inline fun <reified T : Parcelable> gallery(context: Context, imageList: List<T>, loader: ImageWatcherLoader) {
        val intent = Intent(context.applicationContext, ImageWatcherActivity::class.java)
        intent.putExtra(KEY_IMAGEWATCHER_IMAGES, Array(imageList.size) {
            imageList[it]
        })
        this.loader = loader
        context.startActivity(intent)
    }
}