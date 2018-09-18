package me.yangcx.ximagewatcher.loader

import android.os.Parcel
import android.os.Parcelable
import android.widget.ImageView

abstract class ImageWatcherLoader : Parcelable {
    abstract fun loadImage(imageView: ImageView, data: Parcelable)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ImageWatcherLoader> {
        override fun createFromParcel(parcel: Parcel): ImageWatcherLoader {
            return object : ImageWatcherLoader() {
                override fun loadImage(imageView: ImageView, data: Parcelable) {
                }
            }
        }

        override fun newArray(size: Int): Array<ImageWatcherLoader?> {
            return arrayOfNulls(size)
        }
    }
}