package me.yangcx.forrecyclerview.adapter

import android.support.v7.util.DiffUtil
import android.support.v7.util.ListUpdateCallback
import android.util.Log
import me.drakeet.multitype.MultiTypeAdapter
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.BaseBinder
import me.yangcx.forrecyclerview.entity.AdapterDiffUtilCallback
import me.yangcx.forrecyclerview.entity.IAdapterData
import me.yangcx.forrecyclerview.exception.NotImplementsException
import kotlin.reflect.KClass

open class BaseDataAdapter : MultiTypeAdapter() {
    private val diffUtilCallback by lazy {
        AdapterDiffUtilCallback()
    }

    override fun setItems(newItems: MutableList<*>) {
        if (newItems.any { it !is IAdapterData }) {
            throw NotImplementsException()
        }
        val newList = newItems.mapNotNull {
            (it as? IAdapterData)?.copySelf()
        }
        val oldItems = items
        super.setItems(newList)
        val oldList = oldItems.mapNotNull {
            it as? IAdapterData
        }
        diffUtilCallback.setNewSize(newList)
                .setOldSize(oldList)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback, true)
        diffResult.dispatchUpdatesTo(this)
    }

    inline fun <reified T : IAdapterData> register(clazz: KClass<T>, binder: BaseBinder<T>) {
        register(clazz.java, binder)
    }

    inline fun <reified T : IAdapterData> register(clazz: KClass<T>, binderClazz: KClass<BaseBinder<T>>) {
        register(clazz.java, binderClazz.constructors.first().call())
    }

    inline fun <reified T : IAdapterData> register(clazz: KClass<T>): OneToManyFlow<T> {
        return register(clazz.java)
    }
}