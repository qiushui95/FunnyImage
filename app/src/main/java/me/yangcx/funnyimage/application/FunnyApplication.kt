package me.yangcx.funnyimage.application

import android.app.Activity
import android.support.multidex.MultiDexApplication
import com.luliang.shapeutils.DevShapeUtils
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import me.yangcx.funnyimage.di.component.common.DaggerApplicationComponent
import me.yangcx.funnyimage.di.component.common.DaggerGlobalComponent
import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.di.module.common.ApplicationModule
import me.yangcx.funnyimage.log.FileTree
import timber.log.Timber
import javax.inject.Inject

class FunnyApplication : MultiDexApplication(), HasActivityInjector {
    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        initComponents()
        initTimer()
        initDevShape()
    }

    private fun initComponents() {
        DaggerHolder.applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
        DaggerGlobalComponent
                .builder()
                .build()
                .inject(this)
    }

    private fun initTimer() {
        Timber.plant(FileTree(true))
    }

    private fun initDevShape() {
        DevShapeUtils.init(this)
    }

    override fun activityInjector() = activityInjector

}