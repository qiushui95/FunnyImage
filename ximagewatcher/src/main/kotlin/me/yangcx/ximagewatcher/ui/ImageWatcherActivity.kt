package me.yangcx.ximagewatcher.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.PagerSnapHelper
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_watcher_home.*
import me.yangcx.ximagewatcher.R
import me.yangcx.ximagewatcher.utils.ImageWatcher

internal class ImageWatcherActivity : AppCompatActivity() {
    private val imageArray by lazy {
        intent.getParcelableArrayExtra(ImageWatcher.KEY_IMAGEWATCHER_IMAGES)
    }

    private val adapter by lazy {
        ImageWatcherAdapter(layoutInflater, imageArray)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_watcher_home)
        bindRecycler()
        setIndicator(0)
    }

    private fun bindRecycler() {
        rvImage.adapter = adapter
        object : PagerSnapHelper() {
            override fun findTargetSnapPosition(layoutManager: RecyclerView.LayoutManager, velocityX: Int, velocityY: Int): Int {
                val position = super.findTargetSnapPosition(layoutManager, velocityX, velocityY)
                setIndicator(position)
                return position
            }
        }.attachToRecyclerView(rvImage)
    }

    private fun setIndicator(position: Int) {
        tvIndicator.text = getString(R.string.stringXImageWatcherIndicator, position + 1, adapter.itemCount)
    }
}