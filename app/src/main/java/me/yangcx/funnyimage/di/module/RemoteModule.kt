package me.yangcx.funnyimage.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.api.ApiService
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.http.CommonParamInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

@Module
class RemoteModule {
    @Provides
    @RepositoryScope
    fun provideInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.i(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    @RepositoryScope
    fun provideCache(@DirectoryHttpQualifier httpDirectory: File) = Cache(httpDirectory, 100 * 1024 * 1024)

    @Provides
    @RepositoryScope
    fun provideRxAdapter() = RxJavaCallAdapterFactory.create()


    @Provides
    @RepositoryScope
    fun provideClient(commonParamInterceptor: CommonParamInterceptor, logInterceptor: HttpLoggingInterceptor, cache: Cache): OkHttpClient = OkHttpClient.Builder()
            .addInterceptor(logInterceptor)
            .addInterceptor(commonParamInterceptor)
            .cache(cache)
            .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
            .readTimeout(1000, TimeUnit.MILLISECONDS)
            .writeTimeout(1000, TimeUnit.MILLISECONDS)
            .build()

    @Provides
    @RepositoryScope
    fun provideApiService(gson: Gson, rxAdapter: RxJavaCallAdapterFactory, client: OkHttpClient): ApiService {
        return Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/photos/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build()
                .create(ApiService::class.java)
    }
}