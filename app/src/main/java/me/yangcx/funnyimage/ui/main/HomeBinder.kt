package me.yangcx.funnyimage.ui.main

import android.support.v7.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.item_home_image.view.*
import me.yangcx.forrecyclerview.binder.BaseBinder
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.funnyimage.entity.UnsplashImage

class HomeBinder : BaseBinder<ImageDetails>(R.layout.item_home_image) {
    override fun init(holder: RecyclerView.ViewHolder) {
        holder.itemView.ivHome.setOnClickListener {
            val list = mutableListOf<UnsplashImage>()

        }
    }

    override fun drawUi(holder: RecyclerView.ViewHolder, data: ImageDetails) {
        val itemView = holder.itemView
        itemView.tvPosition.text = holder.adapterPosition.toString()
        resetWidth(itemView, data.width, data.height)
        loadCollect(itemView, data.collected)
        loadImage(itemView, data.thumb, data.small)
    }

    override fun uiChanged(holder: RecyclerView.ViewHolder, data: ImageDetails, payloads: List<String>) {
        loadCollect(holder.itemView,data.collected)
    }
    private fun resetWidth(itemView: View, width: Int, height: Int) {
        val lp = itemView.clItem.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.flexGrow = 1f
            val ratio = lp.height * 1f / height
            lp.width = (width * ratio).toInt()
        }
    }

    private fun loadCollect(itemView: View, collected: Boolean) {
        itemView.ivCollect.setImageResource(if (collected) {
            R.drawable.ic_collected
        } else {
            R.drawable.ic_uncollected
        })
    }

    private fun loadImage(itemView: View, thumb: String, small: String) {
        val thumbRequest = Glide.with(itemView).load(thumb)
        Glide.with(itemView).load(small).thumbnail(thumbRequest).apply(RequestOptions().error(R.drawable.bg_splash).dontAnimate()).into(itemView.ivHome)
    }
}