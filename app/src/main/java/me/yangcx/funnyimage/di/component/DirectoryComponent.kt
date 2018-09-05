package me.yangcx.funnyimage.di.component

import dagger.Component
import me.yangcx.funnyimage.di.module.DirectoryModule
import me.yangcx.funnyimage.di.qualifier.DirectoryHttpQualifier
import me.yangcx.funnyimage.log.FileTree
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [DirectoryModule::class],dependencies = [ApplicationComponent::class])
interface DirectoryComponent {
    @DirectoryHttpQualifier
    fun getHttpCache(): File

    fun getLogcatCache(): File

    fun inject(tree: FileTree)
}