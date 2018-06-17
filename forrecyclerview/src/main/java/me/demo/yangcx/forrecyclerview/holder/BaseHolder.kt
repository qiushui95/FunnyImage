package me.demo.yangcx.forrecyclerview.holder

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent
import me.demo.yangcx.forrecyclerview.adapter.BaseDataAdapter

abstract class BaseHolder<T>(private val adapter: BaseDataAdapter, @LayoutRes layoutRes: Int, inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(layoutRes, parent, false)) {
    /**
     * 初始化
     */
    open fun initThis() {

    }

    /**
     * 绘制Item
     */
    abstract fun redrawUI(data: T)

    /**
     * Item局部发生变化、更新局部Item
     */
    fun uiChanged(data: T, payload: Any) {

    }
}