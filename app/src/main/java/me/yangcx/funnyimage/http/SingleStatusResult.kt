package me.yangcx.funnyimage.http

data class SingleStatusResult<T>(var status: StatusEnum = StatusEnum.NOTHING, var errorMessage: String? = null, var data: T? = null)