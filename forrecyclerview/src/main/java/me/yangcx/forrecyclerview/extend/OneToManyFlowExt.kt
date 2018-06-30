package me.yangcx.forrecyclerview.extend

import me.drakeet.multitype.OneToManyEndpoint
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.CommonBinder
import me.yangcx.forrecyclerview.holder.BaseHolder
import kotlin.reflect.KClass

fun <T : Any> OneToManyFlow<T>.to(vararg holderClasses: KClass<BaseHolder<T>>): OneToManyEndpoint<T> {
    val binderArray = Array<CommonBinder<T, BaseHolder<T>>>(holderClasses.size) {
        CommonBinder<T, BaseHolder<T>>(holderClasses[it])
    }
    return this.to(*binderArray)
}