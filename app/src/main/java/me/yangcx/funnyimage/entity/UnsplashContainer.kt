package me.yangcx.funnyimage.entity

import com.google.gson.annotations.SerializedName


data class UnsplashContainer(
        @SerializedName("id") val id: String,
        @SerializedName("urls") val urls: UnsplashImage
) {
    fun convertToImageInfo() = ImageInfo(0, id, urls.thumb, urls.small, urls.regular, urls.full, urls.raw)
}