package me.yangcx.funnyimage.di.module

import android.content.Context
import android.os.Environment
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.di.qualifier.DirectoryLogQualifier
import me.yangcx.funnyimage.di.qualifier.DirectoryLogcatQualifier
import me.yangcx.funnyimage.extend.getChild
import me.yangcx.funnyimage.extend.newChild
import java.io.File

@Module()
class DirectoryModule {
    @Provides
    fun provideRootCache(@ApplicationQualifier context: Context): File {
        return if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState()) || !Environment.isExternalStorageRemovable()) {
            context.externalCacheDir ?: context.cacheDir
        } else {
            context.cacheDir
        }
    }

    @Provides
    @DirectoryLogQualifier
    fun provideLogCache(rootDirectory: File) = rootDirectory.getChild(DIRECTORY_NAME_LOG)

    @Provides
    @DirectoryHttpQualifier
    fun provideHttpCache(@DirectoryLogQualifier logDirectory: File) = logDirectory.newChild(DIRECTORY_NAME_HTTP)

    @Provides
    @DirectoryLogcatQualifier
    fun provideLogcatCache(@DirectoryLogQualifier logDirectory: File) = logDirectory.newChild(DIRECTORY_NAME_LOGCAT)

    companion object {
        private const val DIRECTORY_NAME_LOG = "log"
        private const val DIRECTORY_NAME_HTTP = "http"
        private const val DIRECTORY_NAME_LOGCAT = "logcat"
    }
}