package me.yangcx.xdialog.ask.common

import android.os.Parcel
import android.os.Parcelable
import me.yangcx.xdialog.entity.TextConfig

abstract class AskItemClickListener : Parcelable {

    abstract fun onClick(index: Int, text: TextConfig)
    override fun writeToParcel(parcel: Parcel, flags: Int) {

    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<AskItemClickListener> {
        override fun createFromParcel(parcel: Parcel): AskItemClickListener {
            return object : AskItemClickListener() {
                override fun onClick(index: Int, text: TextConfig) {

                }
            }
        }

        override fun newArray(size: Int): Array<AskItemClickListener?> {
            return arrayOfNulls(size)
        }
    }
}