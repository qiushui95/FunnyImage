package me.yangcx.funnyimage.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.glide.FunnyTransformation
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.weight.FunnyImageView
import timber.log.Timber

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).load(R.mipmap.ic_status_loading).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
        val viewModel = ViewModelProviders.of(this)
                .get(SplashViewModel::class.java)
        viewModel.splashImage.observe(this, Observer<SingleStatusResult<ImageInfo>> {
            Timber.e("image:${it?.status}")
            it?.data?.regular?.also { regular ->
                Glide.with(this).load(regular).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
            }
        })
        viewModel.getSplashImage()
        ivSplash.setOnImageAnimationListener(object : FunnyImageView.OnImageAnimationListener {
            override fun onCompleteOnce() {

            }
        })
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
