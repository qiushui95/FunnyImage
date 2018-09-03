package me.yangcx.funnyimage.ui.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.api.ApiConfig
import me.yangcx.funnyimage.application.FunnyApplication
import me.yangcx.funnyimage.di.component.DaggerSplashComponent
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.reponse.MultipleResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import javax.inject.Inject

class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var retrofit: ApiConfig

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        DaggerSplashComponent.builder()
                .globalComponent(FunnyApplication.get(this).applicationComponent)
                .build()
                .inject(this)
        retrofit.getImageList(0)
                .enqueue(object : Callback<MultipleResult<ImageInfo>> {
                    override fun onFailure(call: Call<MultipleResult<ImageInfo>>, t: Throwable) {
                        Timber.e(t.message ?: "dsds")
                    }

                    override fun onResponse(call: Call<MultipleResult<ImageInfo>>, response: Response<MultipleResult<ImageInfo>>) {
                        response.body()
                                ?.list
                                ?.forEach {
                                    Timber.e(it.toString())
                                }
                    }
                })
    }

}
