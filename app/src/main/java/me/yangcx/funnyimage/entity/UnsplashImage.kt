package me.yangcx.funnyimage.entity

import android.os.Parcel
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
data class UnsplashImage(
        @SerializedName("raw") val raw: String,
        @SerializedName("full") val full: String,
        @SerializedName("regular") val regular: String,
        @SerializedName("small") val small: String,
        @SerializedName("thumb") val thumb: String
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(raw)
        parcel.writeString(full)
        parcel.writeString(regular)
        parcel.writeString(small)
        parcel.writeString(thumb)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UnsplashImage> {
        override fun createFromParcel(parcel: Parcel): UnsplashImage {
            return UnsplashImage(parcel)
        }

        override fun newArray(size: Int): Array<UnsplashImage?> {
            return arrayOfNulls(size)
        }
    }
}