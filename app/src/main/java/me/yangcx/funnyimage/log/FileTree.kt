package me.yangcx.funnyimage.log

import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.extend.getChild
import org.joda.time.DateTime
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import javax.inject.Provider

class FileTree(private val isLoggable: Boolean) : Timber.DebugTree() {
    @Inject
//    @DirectoryLogcatQualifier
    lateinit var cache: File
    @Inject
//    @DirectoryHourNameQualifier
    lateinit var logName: Provider<String>

    init {
        FunnyApplication.directoryComponent
                .inject(this)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable) {
            super.log(priority, tag, message, t)
            val logFile = cache.getChild(logName.get().plus(".txt"))
            val timeString = DateTime.now().toString("HH:mm:ss SS")
            logFile.appendText("$timeString-->$tag:$message\n")
        }
    }

}