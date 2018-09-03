package me.yangcx.xdialog.other

interface XDismissHolder {
    fun dismissWithoutAnimation()
    fun dismissWithoutAnimation(onDismissed: () -> Unit)
}