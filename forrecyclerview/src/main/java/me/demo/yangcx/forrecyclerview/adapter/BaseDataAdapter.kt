package me.demo.yangcx.forrecyclerview.adapter

import android.support.v7.util.DiffUtil
import me.demo.yangcx.forrecyclerview.callback.DiffCallback
import me.demo.yangcx.forrecyclerview.entity.ICopy
import me.demo.yangcx.forrecyclerview.exception.NotImplementsICopyException
import me.drakeet.multitype.MultiTypeAdapter

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
}