package me.yangcx.funnyimage.entity

import com.google.gson.annotations.SerializedName


data class UnsplashContainer(
        @SerializedName("id") val id: String,
        @SerializedName("width") val width: Int,
        @SerializedName("height") val height: Int,
        @SerializedName("urls") val urls: UnsplashImage
) {
    fun convertToImageInfo() = ImageInfo(0, id, width, height, urls.thumb, urls.small, urls.regular, urls.full, urls.raw)
    fun convertToImageDetails() = ImageDetails(id, width, height, urls.thumb, urls.small, urls.regular, urls.full, urls.raw)
}