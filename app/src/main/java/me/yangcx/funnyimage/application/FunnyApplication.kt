package me.yangcx.funnyimage.application

import android.app.Activity
import android.app.Application
import android.support.v4.app.Fragment
import me.yangcx.funnyimage.di.component.DaggerGlobalComponent
import me.yangcx.funnyimage.di.component.GlobalComponent
import me.yangcx.funnyimage.di.module.ApplicationModule
import timber.log.Timber

class FunnyApplication : Application() {
    lateinit var applicationComponent: GlobalComponent
    override fun onCreate() {
        super.onCreate()
        applicationComponent = DaggerGlobalComponent
                .builder()
                .applicationModule(ApplicationModule(this))
                .build()
        initTimer()
    }

    private fun initTimer() {
        Timber.plant(Timber.DebugTree())
    }

    companion object {
        fun get(activity: Activity) = activity.application as FunnyApplication
        fun get(fragment: Fragment) = get(fragment.requireActivity())
    }
}