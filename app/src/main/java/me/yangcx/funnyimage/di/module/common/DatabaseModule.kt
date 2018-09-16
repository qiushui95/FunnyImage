package me.yangcx.funnyimage.di.module.common

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
    fun provideMigrations(): Array<Migration> {
        val list = mutableListOf<Migration>()
        return Array(list.size) {
            list[it]
        }
    }

    @Provides
    @RepositoryScope
    fun provideDatabase(@ApplicationQualifier context: Context, migrations: Array<Migration>): FunnyDatabase {
        return Room
                .databaseBuilder(context, FunnyDatabase::class.java, "funny.db")
                .addMigrations(*migrations)
                .build()
    }

    @Provides
    @RepositoryScope
    fun provideFunnyDao(database: FunnyDatabase): FunnyDao {
        return database.imageDao()
    }
}