package me.yangcx.xdialog.other

import android.os.Parcel
import android.os.Parcelable
import me.yangcx.xdialog.callback.OnDismissedListener
import me.yangcx.xdialog.callback.OnShowInAnimationListener
import me.yangcx.xdialog.callback.OnShowListener
import me.yangcx.xdialog.callback.OnShowOutAnimationListener
import me.yangcx.xdialog.entity.DimensionConfig
import me.yangcx.xdialog.entity.DimensionEnum
import me.yangcx.xdialog.utils.DefaultConfig.DEFAULT_SHOW_TAG

internal data class XDialogConfig(
        val layoutResId: Int,
        val width: DimensionConfig,
        val height: DimensionConfig,
        val dimAmount: Float,
        val canceledOnTouchOutside: Boolean,
        val cancelable: Boolean,
        val gravity: Int,
        var onShowInAnimationListener: OnShowInAnimationListener?,
        val onShowOutAnimationListener: OnShowOutAnimationListener?,
        val showTag: String
) : Parcelable {
    val onShowListenerList = mutableListOf<OnShowListener>()
    val onDismissedListenerList = mutableListOf<OnDismissedListener>()

    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readParcelable<DimensionConfig>(DimensionConfig::class.java.classLoader)
                    ?: DimensionConfig(0f, DimensionEnum.DP, false, true),
            parcel.readParcelable<DimensionConfig>(DimensionConfig::class.java.classLoader)
                    ?: DimensionConfig(0f, DimensionEnum.DP, false, true),
            parcel.readFloat(),
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte(),
            parcel.readInt(),
            parcel.readParcelable<OnShowInAnimationListener>(OnShowInAnimationListener::class.java.classLoader),
            parcel.readParcelable<OnShowOutAnimationListener>(OnShowOutAnimationListener::class.java.classLoader),
            parcel.readString() ?: DEFAULT_SHOW_TAG) {
        parcel.readList(onShowListenerList, OnShowListener::class.java.classLoader)
        parcel.readList(onDismissedListenerList, OnDismissedListener::class.java.classLoader)
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(layoutResId)
        parcel.writeParcelable(width, flags)
        parcel.writeParcelable(height, flags)
        parcel.writeFloat(dimAmount)
        parcel.writeByte(if (canceledOnTouchOutside) 1 else 0)
        parcel.writeByte(if (cancelable) 1 else 0)
        parcel.writeInt(gravity)
        parcel.writeParcelable(onShowInAnimationListener, flags)
        parcel.writeParcelable(onShowOutAnimationListener, flags)
        parcel.writeString(showTag)
        parcel.writeList(onShowListenerList)
        parcel.writeList(onDismissedListenerList)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<XDialogConfig> {
        override fun createFromParcel(parcel: Parcel): XDialogConfig {
            return XDialogConfig(parcel)
        }

        override fun newArray(size: Int): Array<XDialogConfig?> {
            return arrayOfNulls(size)
        }
    }
}