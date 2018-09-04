package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.di.module.DirectoryModule
import me.yangcx.funnyimage.log.FileTree
import javax.inject.Singleton

@Singleton
@Component(modules = [DirectoryModule::class], dependencies = [ApplcationComponent::class])
interface DirectoryComponent {
    fun inject(tree: FileTree)
}