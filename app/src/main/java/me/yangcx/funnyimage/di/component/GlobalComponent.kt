package me.yangcx.funnyimage.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.module.ActivityBindModule
import me.yangcx.funnyimage.di.module.ApplicationModule
import me.yangcx.funnyimage.di.module.WebServiceModule
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.di.scope.GlobalScope
import javax.inject.Singleton

@GlobalScope
@Component(modules = [WebServiceModule::class, AndroidInjectionModule::class, ActivityBindModule::class],dependencies = [DirectoryComponent::class])
interface GlobalComponent {
    fun inject(app: FunnyApplication)
}