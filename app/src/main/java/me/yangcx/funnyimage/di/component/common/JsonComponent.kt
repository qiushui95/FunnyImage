package me.yangcx.funnyimage.di.component.common

import com.google.gson.Gson
import dagger.Component
import me.yangcx.funnyimage.di.module.common.JsonModule

@Component(modules = [JsonModule::class])
interface JsonComponent {
    fun getJson(): Gson
}