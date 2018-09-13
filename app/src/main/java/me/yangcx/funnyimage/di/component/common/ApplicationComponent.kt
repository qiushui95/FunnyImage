package me.yangcx.funnyimage.di.component.common

import android.content.Context
import dagger.Component
import me.yangcx.funnyimage.di.module.common.ApplicationModule
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier

@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {
    @ApplicationQualifier
    fun getContext(): Context
}