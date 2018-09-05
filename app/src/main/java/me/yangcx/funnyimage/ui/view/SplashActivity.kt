package me.yangcx.funnyimage.ui.view

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.aac.model.SplashViewModel

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val viewModel = ViewModelProviders.of(this)
                .get(SplashViewModel::class.java)
    }
}
