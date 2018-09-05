package me.yangcx.funnyimage.reponse

data class SingleResult<T>(val code: Int, val msg: String, val data: T)