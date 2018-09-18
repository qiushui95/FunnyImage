package me.yangcx.ximagewatcher.ui

import android.os.Parcelable
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.yangcx.ximagewatcher.R
import me.yangcx.ximagewatcher.utils.ImageWatcher

internal class ImageWatcherAdapter(private val inflater: LayoutInflater,
                                   private val imageArray: Array<Parcelable>
) : RecyclerView.Adapter<ImageWatcherHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageWatcherHolder {
        val view = inflater.inflate(R.layout.item_watcher_home, parent, false)
        return ImageWatcherHolder(view)
    }

    override fun getItemCount() = imageArray.size

    override fun onBindViewHolder(holder: ImageWatcherHolder, position: Int) {
        ImageWatcher.loader.loadImage(holder.pvImage, imageArray[position])
    }
}