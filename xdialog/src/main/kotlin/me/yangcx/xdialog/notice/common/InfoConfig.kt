package me.yangcx.xdialog.notice.common

import android.os.Parcel
import android.os.Parcelable

data class InfoConfig(var infoType: InfoType, val onCanceledListener: InfoCanceledListener?, var message: String?, var progress: Float, var noticeType: NoticeType, var dismissDelay: Long) : Parcelable {
    constructor(parcel: Parcel) : this(
            InfoType.values()[parcel.readInt()],
            parcel.readParcelable(InfoCanceledListener::class.java.classLoader),
            parcel.readString(),
            parcel.readFloat(),
            NoticeType.values()[parcel.readInt()],
            parcel.readLong()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(infoType.ordinal)
        parcel.writeParcelable(onCanceledListener, flags)
        parcel.writeString(message)
        parcel.writeFloat(progress)
        parcel.writeInt(noticeType.ordinal)
        parcel.writeLong(dismissDelay)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoConfig> {
        override fun createFromParcel(parcel: Parcel): InfoConfig {
            return InfoConfig(parcel)
        }

        override fun newArray(size: Int): Array<InfoConfig?> {
            return arrayOfNulls(size)
        }
    }
}