package me.yangcx.forrecyclerview.extend

import me.drakeet.multitype.OneToManyEndpoint
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.CommonBinder
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

fun <T : Any, VH : BaseHolder<T>> OneToManyFlow<T>.to(vararg holderClasses: KClass<VH>): OneToManyEndpoint<T> {
    val binderArray = Array(holderClasses.size) {
        CommonBinder(holderClasses[it])
    }
    return this.to(*binderArray)
}