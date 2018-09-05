package me.yangcx.funnyimage.entity

import com.google.gson.annotations.SerializedName


data class ImageInfo(
        @SerializedName("id") val id: String,
        @SerializedName("thumb") val thumb: String,
        @SerializedName("small") val small: String,
        @SerializedName("regular") val regular: String,
        @SerializedName("full") val full: String,
        @SerializedName("raw") val raw: String
)