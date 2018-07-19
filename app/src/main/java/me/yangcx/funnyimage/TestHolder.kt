package me.yangcx.funnyimage

import android.view.LayoutInflater
import android.view.ViewGroup
import kotlinx.android.synthetic.main.item_content.view.*
import me.yangcx.forrecyclerview.holder.BaseHolder

class TestHolder(inflater: LayoutInflater, parent: ViewGroup) : BaseHolder<Integer>(R.layout.item_content, inflater, parent) {
    override fun redrawUI(data: Integer) {
        itemView.tvContent.text = String.format("%d",data)
    }
}