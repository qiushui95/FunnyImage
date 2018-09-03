package me.yangcx.xdialog.utils

import android.view.View
import me.yangcx.xdialog.other.XDismissHolder

object DialogAnimationUtils {
    private const val DEFAULT_DURATION = 300L
    fun fadeIn(animationView: View) {
        val animator = animationView.animate()
        animator.cancel()
        animationView.alpha = 0f
        animationView.scaleY = 0f
        animationView.scaleX = 0f
        animator.setDuration(DEFAULT_DURATION)
                .alpha(1f)
                .scaleY(1f)
                .scaleX(1f)
                .start()
    }

    fun fadeOut(animationView: View, holder: XDismissHolder) {
        val animator = animationView.animate()
        animator.cancel()
        animationView.alpha = 1f
        animationView.scaleY = 1f
        animationView.scaleX = 1f
        animator.setDuration(DEFAULT_DURATION)
                .alpha(0f)
                .scaleY(0f)
                .scaleX(0f)
                .withEndAction {
                    holder.dismissWithoutAnimation()
                }
                .start()
    }

    fun bottomIn(animationView: View) {
        val animator = animationView.animate()
        animator.cancel()
        animationView.alpha = 0f
        animationView.translationY = (animationView.parent as View).height - animationView.top * 1f
        animator.setDuration(DEFAULT_DURATION)
                .alpha(1f)
                .translationY(0f)
                .start()
    }

    fun bottomOut(animationView: View, holder: XDismissHolder) {
        val animator = animationView.animate()
        animator.cancel()
        animationView.alpha = 1f
        animationView.translationY = 0f
        animator.setDuration(DEFAULT_DURATION)
                .alpha(0f)
                .translationY((animationView.parent as View).height - animationView.top * 1f)
                .withEndAction {
                    holder.dismissWithoutAnimation()
                }
                .start()
    }
}