package me.demo.yangcx.forrecyclerview.extend

import me.drakeet.multitype.MultiTypeAdapter

inline fun <reified T> MultiTypeAdapter.getItem(position: Int): T? {
    return if (position in 0.until(itemCount)) {
        val item = items[position]
        item as? T
    } else {
        null
    }
}