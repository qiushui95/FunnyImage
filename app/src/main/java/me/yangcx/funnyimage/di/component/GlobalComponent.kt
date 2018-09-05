package me.yangcx.funnyimage.di.component

import dagger.Component
import dagger.android.AndroidInjectionModule
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.module.ActivityBindModule
import me.yangcx.funnyimage.di.module.RemoteModule
import me.yangcx.funnyimage.di.scope.GlobalScope

@GlobalScope
@Component(modules = [AndroidInjectionModule::class, ActivityBindModule::class])
interface GlobalComponent {
    fun inject(app: FunnyApplication)
}