package me.yangcx.xdialog.notice.loading

import me.yangcx.xdialog.notice.common.InfoHolder
import me.yangcx.xdialog.notice.notice.NoticeChange
import me.yangcx.xdialog.notice.progress.ProgressChange
import me.yangcx.xdialog.other.XDismissMoreHolder

interface LoadingHolder : InfoHolder, XDismissMoreHolder, ProgressChange, NoticeChange {

}