package me.yangcx.forrecyclerview.entity

import android.support.v7.util.DiffUtil
import android.util.Log

internal class AdapterDiffUtilCallback : DiffUtil.Callback() {
    private val oldList by lazy { mutableListOf<IAdapterData>() }
    private val newList by lazy { mutableListOf<IAdapterData>() }
    fun setOldSize(oldList: List<IAdapterData>): AdapterDiffUtilCallback {
        this.oldList.clear()
        this.oldList.addAll(oldList)
        return this
    }

    fun setNewSize(newList: List<IAdapterData>): AdapterDiffUtilCallback {
        this.newList.clear()
        this.newList.addAll(newList)
        return this
    }

    override fun getOldListSize() = oldList.size

    override fun getNewListSize() = newList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.isSame(oldItem)
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        return newItem.isContentSame(oldItem)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        val list = mutableListOf<String>()
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]
        newItem.getChangePayload(oldItem, list)
        return list
    }
}