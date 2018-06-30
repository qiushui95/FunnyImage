package me.yangcx.forrecyclerview.adapter

import android.support.annotation.NonNull
import android.support.v7.util.DiffUtil
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.CommonBinder
import me.yangcx.forrecyclerview.callback.DiffCallback
import me.yangcx.forrecyclerview.entity.ICopy
import me.yangcx.forrecyclerview.exception.NotImplementsICopyException
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

abstract class BaseDataAdapter : MultiTypeAdapter(), DiffCallback {
    override fun setItems(newItems: MutableList<*>) {
        if (newItems.count { it is ICopy } != newItems.size) {
            throw NotImplementsICopyException()
        }
        val oldItems = items
        val diffUtilsCallback = object : DiffUtil.Callback() {
            override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return if (oldItem == null) {
                    newItem == null
                } else {
                    if (newItem == null) {
                        false
                    } else {
                        this@BaseDataAdapter.areItemsTheSame(oldItem, newItem)
                    }
                }
            }

            override fun getOldListSize(): Int {
                return oldItems.size
            }

            override fun getNewListSize(): Int {
                return newItems.size
            }

            override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return if (oldItem == null) {
                    newItem == null
                } else {
                    if (newItem == null) {
                        false
                    } else {
                        this@BaseDataAdapter.areContentsTheSame(oldItem, newItem)
                    }
                }
            }

            override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
                val oldItem = oldItems[oldItemPosition]
                val newItem = newItems[newItemPosition]
                return if (oldItem == null || newItem == null) {
                    null
                } else {
                    this@BaseDataAdapter.getChangePayload(oldItem, newItem)
                }
            }
        }
        super.setItems(newItems.map {
            it as ICopy
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