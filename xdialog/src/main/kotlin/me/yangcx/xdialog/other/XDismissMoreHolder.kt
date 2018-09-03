package me.yangcx.xdialog.other

interface XDismissMoreHolder : XDismissHolder {
    fun dismissNow()
    fun dismissNow(onDismissed: () -> Unit)
}