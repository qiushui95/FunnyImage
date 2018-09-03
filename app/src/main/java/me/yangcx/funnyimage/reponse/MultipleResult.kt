package me.yangcx.funnyimage.reponse

data class MultipleResult<T>(val code: Int, val msg: String, val list: List<T>)