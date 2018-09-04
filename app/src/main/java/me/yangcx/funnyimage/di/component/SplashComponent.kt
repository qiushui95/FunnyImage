package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.di.scope.SplashScope
import me.yangcx.funnyimage.ui.view.SplashActivity

@SplashScope
@Component(dependencies = [GlobalComponent::class])
interface SplashComponent {
    fun inject(activity: SplashActivity)
}