package me.yangcx.funnyimage.ui.view

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.constraint.ConstraintSet
import android.support.transition.TransitionManager
import android.support.v7.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.luliang.shapeutils.DevShapeUtils
import com.luliang.shapeutils.selector.DevSelector
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_splash.*
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageInfo
import me.yangcx.funnyimage.glide.FunnyTransformation
import me.yangcx.funnyimage.http.SingleStatusResult
import me.yangcx.funnyimage.http.StatusEnum
import me.yangcx.funnyimage.weight.FunnyImageView
import timber.log.Timber

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
        initAnimation()
        initView()
        initListener()
        Glide.with(this).load(R.mipmap.ic_status_loading).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
        startShowAnimation()
        viewModel.splashImage.observe(this, Observer<SingleStatusResult<ImageInfo>> {
            if (it?.status == StatusEnum.SUCCESS) {
                startHideAnimation()
                it.data?.regular?.also { regular ->
                    Glide.with(this).load(regular).apply(RequestOptions().transform(FunnyTransformation())).into(ivSplash)
                }
                ivSplash.removeAllImageAnimationListener()
                ivSplash.addOnImageAnimationListener(object : FunnyImageView.OnImageAnimationListener {
                    override fun onCompleteOnce() {
                        startShowAnimation()
                    }
                })
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
        viewModel.getSplashImage()
    }

    private fun initView() {
        DevShapeUtils.selectorPressed(R.color.colorPrimary, R.color.colorPrimaryTranslucence)
                .into(tvEnter)
    }

    private fun initListener() {
        ivCollect.setOnClickListener {
            viewModel.collect()
        }
    }

    private fun initAnimation() {
        hideConstraintSet.clone(clRoot)
        showConstraintSet.clone(clRoot)
        showConstraintSet.clear(R.id.ivCollect)
        showConstraintSet.clear(R.id.tvEnter)
        showConstraintSet.connect(R.id.tvEnter, ConstraintSet.END, R.id.clRoot, ConstraintSet.END)
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
