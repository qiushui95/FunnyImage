package me.yangcx.funnyimage.di.module.common

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides

@Module
class JsonModule {
    @Provides
    fun provideJson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }
}