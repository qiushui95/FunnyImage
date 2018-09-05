package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.aac.model.SplashViewModel
import me.yangcx.funnyimage.di.scope.RepositoryScope

@RepositoryScope
@Component(dependencies = [RemoteComponent::class])
interface RepositoryComponent {
    fun inject(viewModel: SplashViewModel)
}