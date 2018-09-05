package me.yangcx.funnyimage.di.module

import android.content.Context
import android.os.Environment
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.extend.getChild
import me.yangcx.funnyimage.extend.newChild
import org.joda.time.DateTime
import java.io.File

@Module
class DirectoryModule {
    private lateinit var rootCache: File

    private fun getRootCache(context: Context): File {
        if (!this::rootCache.isInitialized) {
            rootCache = if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState() || !Environment.isExternalStorageRemovable()) {
                context.externalCacheDir ?: context.cacheDir
            } else {
                context.cacheDir
            }
        }
        return rootCache
    }

    private fun getLogCache(context: Context) = getRootCache(context).getChild(DIRECTORY_NAME_LOG)

    @Provides
    @DirectoryHttpQualifier
    fun provideHttpCache(@ApplicationQualifier context: Context) = getLogCache(context).newChild(DIRECTORY_NAME_HTTP)

    @Provides
//    @DirectoryLogcatQualifier
    fun provideLogcatCache(@ApplicationQualifier context: Context) = getLogCache(context).newChild(DIRECTORY_NAME_LOGCAT)

    @Provides
    fun provideHourName(): String = DateTime().toString("yyyyMMddHH")

    companion object {
        private const val DIRECTORY_NAME_LOG = "log"
        private const val DIRECTORY_NAME_HTTP = "http"
        private const val DIRECTORY_NAME_LOGCAT = "logcat"
    }
}