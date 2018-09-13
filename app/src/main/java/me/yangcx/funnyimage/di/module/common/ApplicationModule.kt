package me.yangcx.funnyimage.di.module.common

import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @ApplicationQualifier
    fun provideContext() = context
}