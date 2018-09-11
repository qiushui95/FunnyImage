package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.di.module.DatabaseModule
import me.yangcx.funnyimage.di.module.RemoteModule
import me.yangcx.funnyimage.di.scope.RepositoryScope
import me.yangcx.funnyimage.ui.splash.SplashViewModel

@RepositoryScope
@Component(modules = [RemoteModule::class, DatabaseModule::class], dependencies = [ApplicationComponent::class,GsonComponent::class, DirectoryComponent::class])
interface RepositoryComponent {
    fun inject(viewModel: SplashViewModel)
}