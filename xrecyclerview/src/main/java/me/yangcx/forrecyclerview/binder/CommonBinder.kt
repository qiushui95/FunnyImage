package me.yangcx.forrecyclerview.binder

import android.view.LayoutInflater
import android.view.ViewGroup
import me.drakeet.multitype.ItemViewBinder
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

class CommonBinder<T, VH : BaseHolder<T>>(private val holderClass: KClass<VH>) : ItemViewBinder<T, VH>() {
    override fun onCreateViewHolder(inflater: LayoutInflater, parent: ViewGroup): VH {
        val holder = holderClass.constructors.first()
                .call(inflater, parent)
        holder.initThis()
        return holder
    }

    override fun onBindViewHolder(holder: VH, item: T) {
        holder.redrawUI(item)
    }

    override fun onBindViewHolder(holder: VH, item: T, payloads: MutableList<Any>) {
        if (payloads.isNotEmpty() && payloads[0] is List<*>) {
            val payload = payloads[0]
            payload as List<*>
            val list = mutableListOf<String>()
            payload.filter {
                it is String
            }.mapTo(list) {
                it as String
            }
            if (list.isNotEmpty()) {
                holder.uiChanged(item, list)
            } else {
                onBindViewHolder(holder, item)
            }
        } else {
            onBindViewHolder(holder, item)
        }
    }
}