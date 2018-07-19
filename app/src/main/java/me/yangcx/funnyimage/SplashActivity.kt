package me.yangcx.funnyimage

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.View
import android.view.WindowManager
import me.yangcx.xdialog.listener.DismissedListener
import me.yangcx.xdialog.listener.OnShowListener
import kotlinx.android.synthetic.main.activity_splash.*
import kotlinx.android.synthetic.main.dialog_test.view.*
import me.drakeet.multitype.MultiTypeAdapter
import me.yangcx.forrecyclerview.binder.CommonBinder

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val adapter = MultiTypeAdapter()
        adapter.register(Integer::class.java, CommonBinder(TestHolder::class))
        rvContent.layoutManager = LinearLayoutManager(this)
        rvContent.adapter = adapter
        adapter.items = (0..100).map { it }
        tvClick.setOnClickListener {
            MyDialog().setWidth(WindowManager.LayoutParams.MATCH_PARENT)
                    .setHeight(100, true)
                    .setOutsideCancelable(true)
                    .setBackPressable(true)
                    .addShowListener(object : OnShowListener {
                        override fun onShow(parent: View) {
                            Log.e("===========", "onShow")
                            parent.tvss.animate()
                                    .rotation(270f)
                                    .start()
                        }
                    })

                    .addDismissedListener(object : DismissedListener {
                        override fun onDismiss() {
                            Log.e("===========", "onDismiss")
                        }
                    })
                    .showImmediately(supportFragmentManager)
        }
    }

}
