package me.yangcx.funnyimage.db

import android.arch.persistence.room.*
import io.reactivex.Flowable
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo

@Dao
interface FunnyDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertImage(imageInfo: ImageInfo?): Flowable<Int>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(imageInfoList: List<ImageInfo>): Flowable<List<Int>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollect(collectInfo: CollectInfo): Flowable<Int>
}