package me.yangcx.funnyimage.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import me.yangcx.funnyimage.entity.ImageInfo

@Dao
interface FunnyDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertImage(imageInfo: ImageInfo?)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(imageInfoList: List<ImageInfo>)

}