package me.yangcx.xdialog.entity

import android.os.Parcel
import android.os.Parcelable

data class DimensionConfig(
        var size: Float,
        var dimensionEnum: DimensionEnum,
        var isPercent: Boolean,
        var isWrap: Boolean
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readFloat(),
            DimensionEnum.values()[parcel.readInt()],
            parcel.readByte() != 0.toByte(),
            parcel.readByte() != 0.toByte()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(size)
        parcel.writeInt(dimensionEnum.ordinal)
        parcel.writeByte(if (isPercent) 1 else 0)
        parcel.writeByte(if (isWrap) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<DimensionConfig> {
        override fun createFromParcel(parcel: Parcel): DimensionConfig {
            return DimensionConfig(parcel)
        }

        override fun newArray(size: Int): Array<DimensionConfig?> {
            return arrayOfNulls(size)
        }
    }
}