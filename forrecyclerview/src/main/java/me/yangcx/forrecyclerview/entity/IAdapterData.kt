package me.yangcx.forrecyclerview.entity

interface IAdapterData {
    fun copySelf(): IAdapterData
    fun isSame(item: Any?): Boolean
    fun isContentSame(item: Any): Boolean
    fun getChangePayload(item: Any, payloadList: MutableList<String>) {

    }
}