package me.yangcx.funnyimage.http

import okhttp3.*

class CommonParamInterceptor(private val params: Map<String, Any>) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val oldRequest = chain.request()
        val body = oldRequest.body()
        if ("POST" == oldRequest.method()) {
            when (body) {
                is FormBody -> {
                    return buildNewResponse(oldRequest, addParamsToFormBody(body, params), chain)
                }
                is MultipartBody -> {
                    return buildNewResponse(oldRequest, addParamsToMultipartBody(body, params), chain)
                }
            }
        } else {
            return buildNewResponse(addParamsToUrl(oldRequest, params), oldRequest, body, chain)
        }
        return chain.proceed(oldRequest)
    }

    companion object {
        private fun addParamsToFormBody(body: FormBody, params: Map<String, Any>): FormBody {
            val bodyBuilder = FormBody.Builder()
            params.asIterable()
                    .forEach {
                        bodyBuilder.add(it.key, it.value.toString())
                    }
            0.until(body.size()).map {
                Pair(body.encodedName(it), body.encodedValue(it))
            }.forEach {
                bodyBuilder.addEncoded(it.first, it.second)
            }
            return bodyBuilder.build()
        }

        private fun addParamsToMultipartBody(body: MultipartBody, params: Map<String, Any>): MultipartBody {
            val bodyBuilder = MultipartBody.Builder()
            bodyBuilder.setType(MultipartBody.FORM)
            params.asIterable()
                    .forEach {
                        bodyBuilder.addFormDataPart(it.key, it.value.toString())

                    }
            body.parts()
                    .forEach {
                        bodyBuilder.addPart(it)
                    }
            return bodyBuilder.build()
        }

        private fun addParamsToUrl(oldRequest: Request, params: Map<String, Any>): HttpUrl {
            val urlBuilder = oldRequest.url()
                    .newBuilder()
                    .scheme(oldRequest.url().scheme())
                    .host(oldRequest.url().host())
            params.asIterable()
                    .forEach {
                        urlBuilder.addQueryParameter(it.key, it.value.toString())
                    }
            return urlBuilder.build()
        }

        private fun buildNewResponse(oldRequest: Request, body: RequestBody?, chain: Interceptor.Chain): Response = buildNewResponse(oldRequest.url(), oldRequest, body, chain)
        private fun buildNewResponse(url: HttpUrl, oldRequest: Request, body: RequestBody?, chain: Interceptor.Chain): Response {
            val newRequest = oldRequest.newBuilder()
                    .url(url)
                    .method(oldRequest.method(), body)
                    .build()
            return chain.proceed(newRequest)
        }
    }
}