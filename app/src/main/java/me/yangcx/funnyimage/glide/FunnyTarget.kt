package me.yangcx.funnyimage.glide

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import java.lang.ref.WeakReference

class FunnyTarget(val imageView: ImageView) : SimpleTarget<Drawable>() {
    private val viewReference by lazy { WeakReference(imageView) }
    override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        viewReference.get()?.setImageDrawable(resource)
    }
}