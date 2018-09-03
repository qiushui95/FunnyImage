package me.yangcx.xdialog.callback

import android.os.Parcel
import android.os.Parcelable

abstract class OnDismissedListener : Parcelable {

    abstract fun onDismissed()
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OnDismissedListener> {
        override fun createFromParcel(parcel: Parcel): OnDismissedListener {
            return object : OnDismissedListener() {
                override fun onDismissed() {

                }
            }
        }

        override fun newArray(size: Int): Array<OnDismissedListener?> {
            return arrayOfNulls(size)
        }
    }
}