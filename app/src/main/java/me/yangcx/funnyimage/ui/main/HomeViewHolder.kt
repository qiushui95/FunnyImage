package me.yangcx.funnyimage.ui.main

import android.support.constraint.ConstraintLayout
import android.view.LayoutInflater
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_home_image.view.*
import me.yangcx.forrecyclerview.holder.BaseHolder
import me.yangcx.funnyimage.R
import me.yangcx.funnyimage.entity.ImageDetails
import timber.log.Timber

class HomeViewHolder(inflater: LayoutInflater, parent: ViewGroup) : BaseHolder<ImageDetails>(R.layout.item_home_image, inflater, parent) {
    override fun redrawUI(data: ImageDetails) {
        Timber.e("=====Holder====,position:$adapterPosition,${data.id}")
        itemView.tvPosition.text=adapterPosition.toString()
        resetRatio(data.width, data.height)
        loadCollect(data.collected)
        loadImage(data.thumb, data.small)
    }

    override fun uiChanged(data: ImageDetails, payloadList: MutableList<String>) {
        if (payloadList.contains("collected")) {
            loadCollect(data.collected)
        }
    }

    private fun resetRatio(width: Int, height: Int) {
        val layoutParams = itemView.ivHome.layoutParams
        if (layoutParams is ConstraintLayout.LayoutParams) {
            layoutParams.dimensionRatio = "$width:$height"
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
        Glide.with(itemView).load(small).thumbnail(thumbRequest).apply(RequestOptions().error(R.drawable.bg_splash)).into(itemView.ivHome)
    }

}