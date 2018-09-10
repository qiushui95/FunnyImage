package me.yangcx.funnyimage.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey

@Entity
class CollectInfo(
        @PrimaryKey
        @ForeignKey(entity = ImageInfo::class, parentColumns = ["id"], childColumns = ["imageId"], onDelete = ForeignKey.CASCADE, onUpdate = ForeignKey.CASCADE, deferred = false)
        val imageId: String,
        val collected: Boolean,
        val updateTime: Long = System.currentTimeMillis()
)