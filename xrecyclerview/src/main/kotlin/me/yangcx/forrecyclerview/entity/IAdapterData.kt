package me.yangcx.forrecyclerview.entity

interface IAdapterData {
    fun copySelf(): IAdapterData
    fun isSame(item: IAdapterData): Boolean
    fun isContentSame(item: IAdapterData): Boolean
    fun getChangePayload(item: IAdapterData, payloadList: MutableList<String>) {

    }
}