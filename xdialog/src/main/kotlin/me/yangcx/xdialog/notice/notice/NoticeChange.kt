package me.yangcx.xdialog.notice.notice

import me.yangcx.xdialog.notice.common.NoticeType

interface NoticeChange {
    fun changeNotice(noticeType: NoticeType,dismissDelay:Long=1500L):NoticeHolder
}