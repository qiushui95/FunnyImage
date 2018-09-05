package me.yangcx.funnyimage.http

import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class CommonParamInterceptor @Inject constructor() : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val builder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter("client_id", "058a82a3ebd6462aeb0d954154ac53c0970a98b3bfb2d2a27c32b6da016413d1")
        val newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(builder.build())
                .build()
        return chain.proceed(newRequest)
    }
}