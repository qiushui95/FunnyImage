package me.yangcx.forrecyclerview.holder

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup

abstract class BaseHolder<T>(@LayoutRes layoutRes: Int, inflater: LayoutInflater, parent: ViewGroup) : RecyclerView.ViewHolder(inflater.inflate(layoutRes, parent, false)) {
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
    open fun uiChanged(data: T, payloadList: MutableList<String>) {

    }
}