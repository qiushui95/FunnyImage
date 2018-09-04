package me.yangcx.funnyimage.di.module

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.di.scope.GlobalScope
import javax.inject.Singleton

@Module
class GsonModule {
    @Provides
    @GlobalScope
    fun provideGson(): Gson {
        val builder = GsonBuilder()
        return builder.create()
    }
}