package me.yangcx.funnyimage.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import javax.inject.Singleton

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @ApplicationQualifier
    fun provideContext() = context
}