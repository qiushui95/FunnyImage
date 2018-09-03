package me.yangcx.xdialog.callback

import android.os.Parcel
import android.os.Parcelable
import android.view.View

abstract class OnShowInAnimationListener : Parcelable {
    abstract fun onShowAnimation(view: View)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<OnShowInAnimationListener> {
        override fun createFromParcel(parcel: Parcel): OnShowInAnimationListener {
            return object : OnShowInAnimationListener() {
                override fun onShowAnimation(view: View) {

                }
            }
        }

        override fun newArray(size: Int): Array<OnShowInAnimationListener?> {
            return arrayOfNulls(size)
        }
    }
}