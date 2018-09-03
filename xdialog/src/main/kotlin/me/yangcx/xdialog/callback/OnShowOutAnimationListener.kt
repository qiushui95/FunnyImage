package me.yangcx.xdialog.callback

import android.os.Parcel
import android.os.Parcelable
import android.view.View
import me.yangcx.xdialog.other.XDismissHolder

abstract class OnShowOutAnimationListener : Parcelable {
    abstract fun onShowAnimation(view: View, holder: XDismissHolder)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OnShowOutAnimationListener> {
        override fun createFromParcel(parcel: Parcel): OnShowOutAnimationListener {
            return object : OnShowOutAnimationListener() {
                override fun onShowAnimation(view: View, holder: XDismissHolder) {

                }
            }
        }

        override fun newArray(size: Int): Array<OnShowOutAnimationListener?> {
            return arrayOfNulls(size)
        }
    }
}