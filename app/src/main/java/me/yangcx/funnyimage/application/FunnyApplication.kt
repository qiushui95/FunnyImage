package me.yangcx.funnyimage.application

import android.app.Activity
import android.app.Application
import android.support.multidex.MultiDexApplication
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.yangcx.funnyimage.di.component.*
import me.yangcx.funnyimage.di.module.ApplicationModule
import me.yangcx.funnyimage.log.FileTree
import timber.log.Timber
import javax.inject.Inject

class FunnyApplication : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        directoryComponent = DaggerDirectoryComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
        globalComponent = DaggerGlobalComponent
                .builder()
                .directoryComponent(directoryComponent)
                .build()
        globalComponent.inject(this)
        initTimer()
    }

    private fun initTimer() {
        Timber.plant(FileTree(true))
    }

    override fun activityInjector() = activityInjector


    companion object {
        lateinit var applicationComponent: ApplicationComponent
        lateinit var directoryComponent: DirectoryComponent
        lateinit var globalComponent: GlobalComponent
        fun get(activity: Activity) = activity.application as FunnyApplication
        fun get(fragment: Fragment) = get(fragment.requireActivity())
    }
}