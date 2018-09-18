package me.yangcx.ximagewatcher.ui

import android.support.v7.widget.RecyclerView
import android.view.View
import com.github.chrisbanes.photoview.PhotoView
import kotlinx.android.synthetic.main.item_watcher_home.view.*

internal class ImageWatcherHolder(view: View) : RecyclerView.ViewHolder(view) {
    val pvImage: PhotoView by lazy {
        itemView.pvImage
    }
}