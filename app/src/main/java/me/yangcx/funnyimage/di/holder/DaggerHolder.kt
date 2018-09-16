package me.yangcx.funnyimage.di.holder

import me.yangcx.funnyimage.di.component.common.*
import me.yangcx.funnyimage.di.module.common.RemoteModule

object DaggerHolder {
    lateinit var applicationComponent: ApplicationComponent
    val jsonComponent by lazy {
        DaggerJsonComponent.builder()
                .build()
    }
    val directoryComponent by lazy {
        DaggerDirectoryComponent.builder()
                .applicationComponent(applicationComponent)
                .build()
    }
    val viewModelComponent: ViewModelComponent by lazy {
        DaggerViewModelComponent.builder()
                .remoteModule(RemoteModule(true,
                        "https://api.unsplash.com/photos/",
                        mapOf(),
                        mapOf(Pair("client_id", "058a82a3ebd6462aeb0d954154ac53c0970a98b3bfb2d2a27c32b6da016413d1"))
                ))
                .jsonComponent(jsonComponent)
                .applicationComponent(applicationComponent)
                .build()
    }
}