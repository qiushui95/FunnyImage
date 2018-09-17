package me.yangcx.funnyimage.di.component.common

import dagger.Component
import me.yangcx.funnyimage.di.module.common.AdapterModule
import me.yangcx.funnyimage.di.module.common.DatabaseModule
import me.yangcx.funnyimage.di.module.common.ServiceModule
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.ui.main.HomeViewModel
import me.yangcx.funnyimage.ui.splash.SplashViewModel

@RepositoryScope
@Component(modules = [ServiceModule::class, DatabaseModule::class], dependencies = [ApplicationComponent::class, JsonComponent::class])
interface ViewModelComponent {
    fun inject(viewModel: SplashViewModel)
    fun inject(viewModel: HomeViewModel)
}