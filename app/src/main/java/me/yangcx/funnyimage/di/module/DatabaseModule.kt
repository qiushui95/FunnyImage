package me.yangcx.funnyimage.di.module

import android.arch.persistence.db.SupportSQLiteDatabase
import android.arch.persistence.room.Room
import android.arch.persistence.room.migration.Migration
import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.db.FunnyDao
import me.yangcx.funnyimage.db.FunnyDatabase
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.scope.RepositoryScope

@Module
class DatabaseModule {
    @Provides
    @RepositoryScope
    fun provideDatabase(@ApplicationQualifier context: Context): FunnyDatabase {
        return Room
                .databaseBuilder(context, FunnyDatabase::class.java, "funny.db")
                .addMigrations(object : Migration(1, 2) {
                    override fun migrate(database: SupportSQLiteDatabase) {
                        database.execSQL("alter table ImageInfo add column createTime INTEGER default 0")
//                        database.execSQL("update ImageInfo set createTime=${System.currentTimeMillis()}")
                    }
                })
                .build()
    }

    @Provides
    @RepositoryScope
    fun provideFunnyDao(database: FunnyDatabase): FunnyDao {
        return database.imageDao()
    }
}