package me.yangcx.funnyimage.http

data class MultipleStatusResult<T>(var status: StatusEnum = StatusEnum.NOTHING, var errorMessage: String? = null, val data: MutableList<T> = mutableListOf())