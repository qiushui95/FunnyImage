package me.yangcx.forrecyclerview.extend

import me.drakeet.multitype.OneToManyEndpoint
import me.drakeet.multitype.OneToManyFlow
import me.yangcx.forrecyclerview.binder.BaseBinder
import me.yangcx.forrecyclerview.entity.IAdapterData
import kotlin.reflect.KClass

fun <T : IAdapterData> OneToManyFlow<T>.to(vararg binderClasses: KClass<BaseBinder<T>>): OneToManyEndpoint<T> {
    val binderArray = Array(binderClasses.size) {
        binderClasses[it].constructors.first().call()
    }
    return this.to(*binderArray)
}