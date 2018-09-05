package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.aac.repository.SplashRepository
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.di.module.RemoteModule
import me.yangcx.funnyimage.di.scope.RemoteScope

@RemoteScope
@Component(modules = [RemoteModule::class], dependencies = [DirectoryComponent::class, GsonComponent::class])
interface RemoteComponent {
    fun getApiConfig(): ApiConfig
    fun inject(repository: SplashRepository)
}