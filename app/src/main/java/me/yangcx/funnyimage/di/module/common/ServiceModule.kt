package me.yangcx.funnyimage.di.module.common

import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.api.ApiService
import retrofit2.Retrofit

@Module(includes = [RemoteModule::class])
class ServiceModule {
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)
}