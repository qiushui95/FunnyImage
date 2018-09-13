package me.yangcx.funnyimage.di.module.common

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.http.CommonHeaderInterceptor
import me.yangcx.funnyimage.http.CommonParamInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import timber.log.Timber
import java.io.File
import java.util.concurrent.TimeUnit

@Module(includes = [DirectoryModule::class])
class RemoteModule(private val printLog: Boolean,
                   private val baseUrl: String,
                   private val commonHeader: Map<String, Any>,
                   private val commonParam: Map<String, Any>) {
    init {
        if (!baseUrl.endsWith("/")) {
            throw IllegalArgumentException("baseUrl must end with '/',now is $baseUrl;")
        }
    }

    @Provides
    fun provideLogInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor {
            Timber.d(it)
        }
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        return interceptor
    }

    @Provides
    fun provideCommonParamInterceptor() = CommonParamInterceptor(commonParam)

    @Provides
    fun provideCommonHeaderInterceptor() = CommonHeaderInterceptor(commonHeader)

    @Provides
    fun provideCache(cacheDir: File) = Cache(File(cacheDir, "http"), 100 * 1024 * 1024)

    @Provides
    fun provideRxAdapter() = RxJava2CallAdapterFactory.create()


    @Provides
    fun provideClient(logInterceptor: HttpLoggingInterceptor, commonHeaderInterceptor: CommonHeaderInterceptor, commonParamInterceptor: CommonParamInterceptor, cache: Cache): OkHttpClient {
        val builder = OkHttpClient.Builder()
        if (printLog) {
            builder.addInterceptor(logInterceptor)
        }
        builder.addInterceptor(commonHeaderInterceptor)
                .addInterceptor(commonParamInterceptor)
                .cache(cache)
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .readTimeout(5 * 1000, TimeUnit.MILLISECONDS)
                .writeTimeout(5 * 1000, TimeUnit.MILLISECONDS)
        return builder.build()
    }

    @Provides
    fun provideApiService(json: Gson, rxAdapter: RxJava2CallAdapterFactory, client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create(json))
                .addCallAdapterFactory(rxAdapter)
                .client(client)
                .build()
    }
}