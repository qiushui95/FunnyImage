package me.yangcx.funnyimage.application

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.yangcx.funnyimage.di.component.ApplcationComponent
import me.yangcx.funnyimage.di.component.DaggerApplcationComponent
import me.yangcx.funnyimage.di.component.DaggerGlobalComponent
import me.yangcx.funnyimage.di.component.GlobalComponent
import me.yangcx.funnyimage.di.module.ApplicationModule
import timber.log.Timber
import javax.inject.Inject

class FunnyApplication : Application(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerApplcationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        globalComponent = DaggerGlobalComponent
                .builder()
                .applcationComponent(applicationComponent)
                .build()
        globalComponent.inject(this)
        initTimer()
    }

    private fun initTimer() {
        Timber.plant(Timber.DebugTree())
    }

    override fun activityInjector() = activityInjector


    companion object {
        lateinit var applicationComponent: ApplcationComponent
        lateinit var globalComponent: GlobalComponent
        fun get(activity: Activity) = activity.application as FunnyApplication
        fun get(fragment: Fragment) = get(fragment.requireActivity())
    }
}