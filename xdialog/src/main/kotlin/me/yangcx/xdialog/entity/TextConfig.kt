package me.yangcx.xdialog.entity

import android.os.Parcel
import android.os.Parcelable
import android.support.annotation.ColorInt

data class TextConfig(var text: CharSequence?, @ColorInt var textColor: Int, var isBold: Boolean, var textSize: Float, var dimensionEnum: DimensionEnum) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readInt(),
            parcel.readByte() != 0.toByte(),
            parcel.readFloat(),
            DimensionEnum.values()[parcel.readInt()])

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(text?.toString())
        parcel.writeInt(textColor)
        parcel.writeByte(if (isBold) 1 else 0)
        parcel.writeFloat(textSize)
        parcel.writeInt(dimensionEnum.ordinal)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TextConfig> {
        override fun createFromParcel(parcel: Parcel): TextConfig {
            return TextConfig(parcel)
        }

        override fun newArray(size: Int): Array<TextConfig?> {
            return arrayOfNulls(size)
        }
    }
}