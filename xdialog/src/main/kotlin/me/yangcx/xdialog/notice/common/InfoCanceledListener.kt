package me.yangcx.xdialog.notice.common

import android.os.Parcel
import android.os.Parcelable

abstract class InfoCanceledListener : Parcelable {

    abstract fun onCanceled(infoType: InfoType?)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<InfoCanceledListener> {
        override fun createFromParcel(parcel: Parcel): InfoCanceledListener {
            return object : InfoCanceledListener() {
                override fun onCanceled(infoType: InfoType?) {

                }
            }
        }

        override fun newArray(size: Int): Array<InfoCanceledListener?> {
            return arrayOfNulls(size)
        }
    }

}