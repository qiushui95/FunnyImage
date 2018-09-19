package me.yangcx.xnetwork.extend


inline fun <reified T> MutableList<T>.addAll(index: Int, elements: Collection<T>?) = if (elements != null) {
    addAll(index, elements)
} else {
    false
}


inline fun <reified T> MutableList<T>.addAllFirst(elements: Collection<T>?) = addAll(0, elements)


inline fun <reified T> MutableList<T>.addAllLast(elements: Collection<T>?) = addAll(size, elements)
inline fun <reified T> MutableList<T>.addAll(elements: Collection<T>?) = addAllLast(elements)
