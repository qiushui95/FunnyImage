package me.yangcx.funnyimage.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import me.yangcx.funnyimage.entity.CollectInfo
import me.yangcx.funnyimage.entity.ImageInfo

@Database(entities = [ImageInfo::class, CollectInfo::class], version = 1, exportSchema = true)
abstract class FunnyDatabase : RoomDatabase() {
    abstract fun imageDao(): FunnyDao
}