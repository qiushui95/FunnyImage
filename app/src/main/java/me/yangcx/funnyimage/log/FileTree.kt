package me.yangcx.funnyimage.log

import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.component.DaggerDirectoryComponent
import me.yangcx.funnyimage.di.qualifier.DirectoryLogcatQualifier
import timber.log.Timber
import java.io.File
import javax.inject.Inject

class FileTree : Timber.DebugTree() {
    @Inject
    @DirectoryLogcatQualifier
    lateinit var cache: File
    init {
        DaggerDirectoryComponent.builder()
                .applcationComponent(FunnyApplication.applicationComponent)
                .build()
                .inject(this)
    }

    override fun log(priority: Int, t: Throwable?) {
        super.log(priority, t)

    }
    
}