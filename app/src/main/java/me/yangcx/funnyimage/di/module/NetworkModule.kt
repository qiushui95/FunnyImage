package me.yangcx.funnyimage.di.module

import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.di.scope.GlobalScope
import me.yangcx.funnyimage.http.CommonParamInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.io.File

@Module
class NetworkModule {
    @Provides
    @GlobalScope
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @GlobalScope
    fun provideCache(@DirectoryHttpQualifier httpDirectory: File) = Cache(httpDirectory, 100 * 1024 * 1024)

    @Provides
    @GlobalScope
    fun provideClient(commonParamInterceptor: CommonParamInterceptor, logInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(commonParamInterceptor)
            .cache(cache)
            .build()
}