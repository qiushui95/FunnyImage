package me.yangcx.funnyimage.di.component.common

import dagger.Component
import me.yangcx.funnyimage.di.module.common.DirectoryModule
import me.yangcx.funnyimage.log.FileTree
import java.io.File

@Component(modules = [DirectoryModule::class], dependencies = [ApplicationComponent::class])
interface DirectoryComponent {
//    @RootCacheQualifier
    fun getRootCache(): File

    fun inject(tree: FileTree)
}