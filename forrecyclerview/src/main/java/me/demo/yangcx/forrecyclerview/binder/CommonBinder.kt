package me.demo.yangcx.forrecyclerview.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import me.demo.yangcx.forrecyclerview.holder.BaseHolder
import me.drakeet.multitype.ItemViewBinder
import kotlin.reflect.KClass

class CommonBinder<T, VH : BaseHolder<T>>(private val holderClass: KClass<VH>) : ItemViewBinder<T, VH>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        val holder = holderClass.constructors.first()
                .call(adapter, inflater, parent)
        holder.initThis()
        return holder
    }

    override fun onBindViewHolder(holder: VH, item: T) {
        holder.redrawUI(item)
    }

    override fun onBindViewHolder(holder: VH, item: T, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, item)
        } else {
            holder.uiChanged(item, payloads[0])
        }
    }
}