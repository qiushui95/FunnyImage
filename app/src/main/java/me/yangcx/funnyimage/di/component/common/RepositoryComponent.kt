package me.yangcx.funnyimage.di.component.common

import dagger.Component
import me.yangcx.funnyimage.di.module.common.DatabaseModule
import me.yangcx.funnyimage.di.module.common.ServiceModule
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.ui.splash.SplashViewModel

@RepositoryScope
@Component(modules = [ServiceModule::class, DatabaseModule::class], dependencies = [ApplicationComponent::class, JsonComponent::class])
interface RepositoryComponent {
    fun inject(viewModel: SplashViewModel)
}