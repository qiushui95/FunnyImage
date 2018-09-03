package me.yangcx.forrecyclerview.adapter

import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.util.Log
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.CommonBinder
import me.yangcx.forrecyclerview.entity.IAdapterData
import me.yangcx.forrecyclerview.exception.NotImplementsICopyException
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

open class BaseDataAdapter : MultiTypeAdapter() {

    override fun setItems(newItems: MutableList<*>) {
        if (newItems.count { it is IAdapterData } != newItems.size) {
            throw NotImplementsICopyException()
        }
        val oldItems = items
        val diffUtilsCallback = object : DiffUtil.Callback() {
            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem is IAdapterData && newItem is IAdapterData && newItem.isSame(oldItem)
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return oldItem is IAdapterData && newItem is IAdapterData && newItem.isContentSame(oldItem)
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return if (oldItem is IAdapterData && newItem is IAdapterData) {
                    val list = mutableListOf<String>()
                    newItem.getChangePayload(oldItem, list)
                    if (list.isEmpty()) {
                        null
                    } else {
                        list
                    }
                } else {
                    null
                }
            }
        }
        super.setItems(newItems.map {
            it as IAdapterData
        }.map {
            it.copySelf()
        })
        val diffResult = DiffUtil.calculateDiff(diffUtilsCallback, true)
        diffResult.dispatchUpdatesTo(this)
    }

    inline fun <reified T> getItem(position: Int): T? {
        return if (position in 0.until(itemCount)) {
            val item = items[position]
            item as? T
        } else {
            null
        }
    }

    fun <T : Any, VH : BaseHolder<T>> register(@NonNull clazz: KClass<T>, @NonNull holderClass: KClass<VH>) {
        register(clazz.java, CommonBinder(holderClass))
    }

    fun <T : Any> register(@NonNull clazz: KClass<T>): OneToManyFlow<T> {
        return register(clazz.java)
    }
}