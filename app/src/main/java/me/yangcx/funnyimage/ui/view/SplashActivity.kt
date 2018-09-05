package me.yangcx.funnyimage.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.ViewTreeObserver
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.ViewTarget
import com.bumptech.glide.request.transition.Transition
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.aac.model.SplashViewModel
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.weight.FunnyImageView
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val viewModel = ViewModelProviders.of(this)
                .get(SplashViewModel::class.java)
        viewModel.splashImage.observe(this, Observer<ImageInfo> {
            Glide.with(this).asBitmap().load(it?.regular).into(object : ViewTarget<FunnyImageView, Bitmap>(ivSplash) {
                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    ivSplash.setImage(resource)
                    ivSplash.setBackgroundColor(Color.YELLOW)
                }
            })
        })
        viewModel.getSplashImage()
    }

    override fun onResume() {
        super.onResume()
        ivSplash.resume()
    }

    override fun onPause() {
        super.onPause()
        ivSplash.pause()
    }
}
