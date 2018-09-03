package me.yangcx.funnyimage.di.component

import com.google.gson.Gson
import dagger.Component
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.di.module.ApplicationModule
import me.yangcx.funnyimage.di.module.GsonModule
import me.yangcx.funnyimage.di.module.WebServiceModule
import me.yangcx.funnyimage.di.scope.GlobalScope

@GlobalScope
@Component(modules = arrayOf(ApplicationModule::class, GsonModule::class, WebServiceModule::class))
interface GlobalComponent {
    fun getGson(): Gson
    fun getApiConfig(): ApiConfig
}