package me.yangcx.funnyimage.ui.main

import android.os.Parcelable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.flexbox.FlexboxLayoutManager
import kotlinx.android.synthetic.main.item_home_image.view.*
import me.yangcx.forrecyclerview.holder.BaseViewModelHolder
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageDetails
import me.yangcx.funnyimage.entity.UnsplashImage
import me.yangcx.ximagewatcher.loader.ImageWatcherLoader
import me.yangcx.ximagewatcher.utils.ImageWatcher

class HomeViewHolder(inflater: LayoutInflater, parent: ViewGroup) : BaseViewModelHolder<ImageDetails, HomeViewModel>(HomeViewModel::class, R.layout.item_home_image, inflater, parent) {
    override fun initThis() {
        super.initThis()
        itemView.ivHome.setOnClickListener {
            val list = mutableListOf<UnsplashImage>()
            viewModel.dataList.value?.dataList?.mapTo(list) { images ->
                UnsplashImage(images.raw, images.full, images.regular, images.small, images.thumb)
            }
            if (list.isNotEmpty()) {
                ImageWatcher.gallery(itemView.context, list, object : ImageWatcherLoader() {
                    override fun loadImage(imageView: ImageView, data: Parcelable) {
                        Glide.with(imageView).load((data as UnsplashImage).full).into(imageView)
                    }
                })
            }
        }
    }

    override fun redrawUI(data: ImageDetails) {
        itemView.tvPosition.text = adapterPosition.toString()
        resetWidth(data.width, data.height)
        loadCollect(data.collected)
        loadImage(data.thumb, data.small)
    }

    override fun uiChanged(data: ImageDetails, payloadList: List<String>) {
        if (payloadList.contains("collected")) {
            loadCollect(data.collected)
        }
    }

    private fun resetWidth(width: Int, height: Int) {
        val lp = itemView.clItem.layoutParams
        if (lp is FlexboxLayoutManager.LayoutParams) {
            lp.flexGrow = 1f
            val ratio = lp.height * 1f / height
            lp.width = (width * ratio).toInt()
        }
    }

    private fun loadCollect(collected: Boolean) {
        itemView.ivCollect.setImageResource(if (collected) {
            R.drawable.ic_collected
        } else {
            R.drawable.ic_uncollected
        })
    }

    private fun loadImage(thumb: String, small: String) {
        val thumbRequest = Glide.with(itemView).load(thumb)
        Glide.with(itemView).load(small).thumbnail(thumbRequest).apply(RequestOptions().error(R.drawable.bg_splash).dontAnimate()).into(itemView.ivHome)
    }

}