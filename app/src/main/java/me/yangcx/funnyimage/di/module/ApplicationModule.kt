package me.yangcx.funnyimage.di.module

import android.content.Context
import dagger.Module
import dagger.Provides
import me.yangcx.funnyimage.di.qualifier.Application
import me.yangcx.funnyimage.di.scope.GlobalScope

@Module
class ApplicationModule(private val context: Context) {
    @Provides
    @GlobalScope
    @Application
    fun provideContext() = context
}