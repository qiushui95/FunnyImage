package me.yangcx.funnyimage.http

import okhttp3.Interceptor
import okhttp3.Response

class CommonHeaderInterceptor(private val params: Map<String, Any>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val newBuilder = oldRequest.newBuilder()
        params.asIterable()
                .forEach {
                    newBuilder.addHeader(it.key, it.value.toString())
                }
        val request = newBuilder
                .build()
        return chain.proceed(request)
    }
}