package me.yangcx.funnyimage.di.module.common

import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.qualifier.RootCacheQualifier
import me.yangcx.xfoundation.utils.DirectoryUtils

@Module
class DirectoryModule {
    @Provides
//    @RootCacheQualifier
    fun provideCacheDir(@ApplicationQualifier context: Context) = DirectoryUtils.getRootCache(context)
}