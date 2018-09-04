package me.yangcx.funnyimage.di.module

import dagger.Module
import dagger.android.ContributesAndroidInjector
import me.yangcx.funnyimage.di.scope.ActivityScope
import me.yangcx.funnyimage.ui.view.SplashActivity

@Module
abstract class ActivityBindModule {
    @ActivityScope
    @ContributesAndroidInjector
    abstract fun splashInjector(): SplashActivity
}