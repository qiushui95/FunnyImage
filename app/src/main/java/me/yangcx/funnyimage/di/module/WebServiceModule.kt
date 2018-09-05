package me.yangcx.funnyimage.di.module

import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.di.scope.GlobalScope
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module(includes = [GsonModule::class, NetworkModule::class])
class WebServiceModule {
    @Provides
    @GlobalScope
    fun provideApiService(gson: Gson, client: OkHttpClient): ApiConfig {
        return Retrofit.Builder()
                .baseUrl("https://api.unsplash.com/photos/")
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build()
                .create(ApiConfig::class.java)
    }
}