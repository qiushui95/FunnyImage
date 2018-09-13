package me.yangcx.funnyimage.di.component.common

import dagger.Component
import dagger.android.AndroidInjectionModule
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.module.common.ActivityBindModule
import me.yangcx.funnyimage.di.scope.GlobalScope

@GlobalScope
@Component(modules = [AndroidInjectionModule::class, ActivityBindModule::class])
interface GlobalComponent {
    fun inject(app: FunnyApplication)
}