package me.yangcx.funnyimage.ui.splash

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.jakewharton.rxbinding2.view.RxView
import com.luliang.shapeutils.DevShapeUtils
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.glide.FunnyTransformation
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import java.util.concurrent.TimeUnit

class SplashActivity : AppCompatActivity() {
    private val viewModel by lazy {
        ViewModelProviders.of(this)
                .get(SplashViewModel::class.java)
    }
    private val hideConstraintSet by lazy {
        ConstraintSet()
    }
    private val showConstraintSet by lazy {
        ConstraintSet()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        Glide.with(this).load(R.mipmap.ic_status_loading).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
        viewModel.splashImage.observe(this, Observer<SingleStatusResult<ImageInfo>> {
            if (it?.status == StatusEnum.SUCCESS) {
                it.data?.regular?.also { regular ->
                    Glide.with(this).load(regular).apply(
                            RequestOptions()
                                    .transform(FunnyTransformation()))
                            .addListener(object : RequestListener<Drawable> {
                                override fun onLoadFailed(p0: GlideException?, p1: Any?, p2: Target<Drawable>?, p3: Boolean): Boolean {
                                    return false
                                }

                                override fun onResourceReady(p0: Drawable?, p1: Any?, p2: Target<Drawable>?, p3: DataSource?, p4: Boolean): Boolean {
                                    viewModel.setImageClickable(true)
                                    return false
                                }
                            }
                            ).into(ivSplash)
                }
            }
        })
        viewModel.collectStatus.observe(this, Observer<SingleStatusResult<Boolean>> {
            ivCollect.isClickable = it?.status != StatusEnum.LOADING
            ivCollect.setImageResource(if (it?.data == true) {
                R.drawable.ic_collected
            } else {
                R.drawable.ic_uncollected
            })
        })
        viewModel.buttonsIsHiding.observe(this, Observer {
            if (it == true) {
                startShowAnimation()
            } else {
                startHideAnimation()
            }
        })
        viewModel.imageClickable.observe(this, Observer {
            ivSplash.isClickable = it == true
        })
        viewModel.getSplashImage()
        initAnimation()
        initView()
        initListener()
    }

    private fun initView() {
        DevShapeUtils.selectorPressed(R.color.colorPrimaryTranslucence, R.color.colorPrimary)
                .into(tvEnter)
        viewModel.setImageClickable(false)
    }

    private fun initListener() {
        RxView.clicks(ivSplash)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.toggleButtonStatus()
                }.isDisposed
        RxView.clicks(ivCollect)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    viewModel.collect()
                }.isDisposed
        RxView.clicks(tvEnter)
                .throttleFirst(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {

                }.isDisposed

    }

    private fun initAnimation() {
        hideConstraintSet.clone(clRoot)
        showConstraintSet.clone(clRoot)
        showConstraintSet.clear(R.id.tvEnter, ConstraintSet.START)
        showConstraintSet.connect(R.id.tvEnter, ConstraintSet.END, R.id.clRoot, ConstraintSet.END)
        showConstraintSet.clear(R.id.ivCollect, ConstraintSet.START)
        showConstraintSet.connect(R.id.ivCollect, ConstraintSet.END, R.id.tvEnter, ConstraintSet.START)
    }

    private fun startShowAnimation() {
        TransitionManager.beginDelayedTransition(clRoot)
        showConstraintSet.applyTo(clRoot)
    }

    private fun startHideAnimation() {
        TransitionManager.beginDelayedTransition(clRoot)
        hideConstraintSet.applyTo(clRoot)
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
