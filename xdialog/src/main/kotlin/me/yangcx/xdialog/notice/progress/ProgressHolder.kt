package me.yangcx.xdialog.notice.progress

import me.yangcx.xdialog.notice.common.InfoHolder
import me.yangcx.xdialog.notice.loading.LoadingChange
import me.yangcx.xdialog.notice.notice.NoticeChange
import me.yangcx.xdialog.other.XDismissMoreHolder

interface ProgressHolder : InfoHolder, XDismissMoreHolder, LoadingChange, NoticeChange {
    fun updateProgress(progress: Float)
}