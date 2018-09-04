package me.yangcx.funnyimage.di.component

import android.content.Context
import dagger.Component
import dagger.android.AndroidInjectionModule
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.module.ActivityBindModule
import me.yangcx.funnyimage.di.module.ApplicationModule
import me.yangcx.funnyimage.di.module.WebServiceModule
import me.yangcx.funnyimage.di.qualifier.ApplicationQualifier
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.di.scope.GlobalScope
import javax.inject.Singleton

@Component(modules = [ApplicationModule::class])
interface ApplcationComponent {
    @ApplicationQualifier
    fun getContext(): Context
}