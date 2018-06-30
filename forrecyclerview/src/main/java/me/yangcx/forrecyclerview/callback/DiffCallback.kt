package me.yangcx.forrecyclerview.callback

internal interface DiffCallback {
    fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean
    fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean
    fun getChangePayload(oldItem: Any, newItem: Any): Any? {
        return null
    }
}