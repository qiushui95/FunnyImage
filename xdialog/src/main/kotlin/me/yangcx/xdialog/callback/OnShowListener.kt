package me.yangcx.xdialog.callback

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import me.yangcx.xdialog.other.XDialogHolder

abstract class OnShowListener : Parcelable {
    abstract fun onShow(view: View, holder: XDialogHolder)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OnShowListener> {
        override fun createFromParcel(parcel: Parcel): OnShowListener {
            return object : OnShowListener() {
                override fun onShow(view: View, holder: XDialogHolder) {

                }
            }
        }

        override fun newArray(size: Int): Array<OnShowListener?> {
            return arrayOfNulls(size)
        }
    }
}