package me.yangcx.funnyimage.db

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Maybe
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.funnyimage.entity.ImageInfo

@Dao
interface FunnyDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertImage(imageInfo: ImageInfo?): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertImage(imageInfoList: List<ImageInfo>): List<Long>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCollect(collectInfo: CollectInfo): Long

//    @Query("select `index`,id,width,height,thumb,small,regular,full,raw,collected >0 as collected from imageinfo left join collectinfo on imageId=id order by `index` desc limit 10 offset 0")
//    fun getHomeList(): Maybe<List<ImageDetails>>
}