package me.yangcx.funnyimage.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.di.scope.GlobalScope
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File
import javax.inject.Singleton

@Module(includes = [DirectoryModule::class])
class NetworkModule {
    @Provides
    @GlobalScope
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i("http:%s", it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BASIC
        return interceptor
    }

    @Provides
    @GlobalScope
    fun provideCache(@DirectoryHttpQualifier httpDirectory: File) = Cache(httpDirectory, 100 * 1024 * 1024)

    @Provides
    @GlobalScope
    fun provideClient(interceptor: HttpLoggingInterceptor, cache: Cache) = OkHttpClient.Builder()
            .addInterceptor(interceptor)
            .cache(cache)
            .build()
}