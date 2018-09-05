package me.yangcx.funnyimage.di.component

import com.google.gson.Gson
import dagger.Component
import me.yangcx.funnyimage.di.module.GsonModule

@Component(modules = [GsonModule::class])
interface GsonComponent {
    fun getGson(): Gson
}