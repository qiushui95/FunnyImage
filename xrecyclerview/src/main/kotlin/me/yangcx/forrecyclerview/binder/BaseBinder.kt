package me.yangcx.forrecyclerview.binder

import android.support.annotation.LayoutRes
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import me.drakeet.multitype.ItemViewBinder
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

abstract class BaseBinder<T>(@LayoutRes private val layoutRes: Int) : ItemViewBinder<T, RecyclerView.ViewHolder>() {
    /**
     * 初始化
     */
    abstract fun init(holder: RecyclerView.ViewHolder)

    /**
     * 绘制item界面
     */
    abstract fun drawUi(holder: RecyclerView.ViewHolder, data: T)

    /**
     * 数据局部变化、重绘局部界面
     */
    protected open fun uiChanged(holder: RecyclerView.ViewHolder, data: T, payloads: List<String>) {

    }

    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): RecyclerView.ViewHolder {
        val holder = BaseHolder(inflater.inflate(layoutRes, parent, false))
        init(holder)
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: T) {
        drawUi(holder, item)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, item: T, payloads: MutableList<Any>) {
        val firstItem = payloads.firstOrNull()
        if (firstItem != null && firstItem is List<*>) {
            val list = firstItem.mapNotNull {
                it as? String
            }
            uiChanged(holder, item, list)
        } else {
            onBindViewHolder(holder, item)
        }
    }
}