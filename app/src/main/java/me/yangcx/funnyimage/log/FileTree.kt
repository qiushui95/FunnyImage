package me.yangcx.funnyimage.log

import me.yangcx.funnyimage.di.holder.DaggerHolder
import me.yangcx.funnyimage.utils.TimeStampUtils
import me.yangcx.xfoundation.extend.getChild
import org.joda.time.DateTime
import timber.log.Timber
import java.io.File
import javax.inject.Inject
import kotlin.concurrent.thread

class FileTree(private val isLoggable: Boolean) : Timber.DebugTree() {
    @Inject
//    @DirectoryLogcatQualifier
    lateinit var cache: File

    init {
        DaggerHolder.directoryComponent
                .inject(this)
    }

    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (isLoggable) {
            super.log(priority, tag, message, t)
            thread {
                val logFile = cache.getChild("Log-".plus(TimeStampUtils.getNowString("yyyyMMddHH")).plus(".txt"))
                val timeString = DateTime.now().toString("HH:mm:ss SS")
                logFile.appendText("$timeString-->$tag:$message\n")
            }
        }
    }

}