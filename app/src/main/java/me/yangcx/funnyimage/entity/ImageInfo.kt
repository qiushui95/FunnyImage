package me.yangcx.funnyimage.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import me.yangcx.funnyimage.annotation.POJO

@POJO
@Entity(indices = [Index("id", unique = true)])
open class ImageInfo(
        @PrimaryKey(autoGenerate = true)
        var index: Long,
        var id: String,
        var width: Int,
        var height: Int,
        var thumb: String,
        var small: String,
        var regular: String,
        var full: String,
        var raw: String,
        var insertTime: Long
)