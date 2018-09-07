package me.yangcx.funnyimage.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey

@Entity(indices = [Index("id", unique = true)])
data class ImageInfo(
        @PrimaryKey(autoGenerate = true)
        val index: Long,
        val id: String,
        val thumb: String,
        val small: String,
        val regular: String,
        val full: String,
        val raw: String
)