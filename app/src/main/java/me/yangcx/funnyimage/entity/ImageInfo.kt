package me.yangcx.funnyimage.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class ImageInfo(
        @PrimaryKey
        val id: String,
        val thumb: String,
        val small: String,
        val regular: String,
        val full: String,
        val raw: String,
        val createTime: Long = System.currentTimeMillis()
)