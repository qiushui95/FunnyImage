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
import me.yangcx.funnyimage.viewmodel.SplashViewModel
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.glide.FunnyTransformation

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val viewModel = ViewModelProviders.of(this)
                .get(SplashViewModel::class.java)
        viewModel.splashImage.observe(this, Observer<ImageInfo> {
            Glide.with(this).load(it?.regular).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
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
