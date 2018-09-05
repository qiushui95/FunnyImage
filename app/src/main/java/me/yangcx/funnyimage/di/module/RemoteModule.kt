package me.yangcx.funnyimage.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.di.scope.GlobalScope
import me.yangcx.funnyimage.di.scope.RemoteScope
import me.yangcx.funnyimage.http.CommonParamInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File

@Module
class RemoteModule {
    @Provides
    @RemoteScope
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @RemoteScope
    fun provideCache(@DirectoryHttpQualifier httpDirectory: File) = Cache(httpDirectory, 100 * 1024 * 1024)

    @Provides
    @RemoteScope
    fun provideClient(commonParamInterceptor: CommonParamInterceptor, logInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(commonParamInterceptor)
            .cache(cache)
            .build()

    @Provides
    @RemoteScope
    fun provideApiService(gson: Gson, client: OkHttpClient): ApiConfig {
        return Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/photos/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(ApiConfig::class.java)
    }
}