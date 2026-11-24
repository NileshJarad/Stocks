package com.android.stocks.data.mock

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockHoldingsInterceptor @Inject constructor(
    @ApplicationContext private val context: Context,
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val body = context.assets.open(ASSET_NAME).bufferedReader().use { it.readText() }

        return Response.Builder()
            .request(request)
            .protocol(Protocol.HTTP_1_1)
            .code(200)
            .message("OK")
            .body(body.toResponseBody(JSON_MEDIA_TYPE))
            .build()
    }

    private companion object {
        const val ASSET_NAME = "holdings.json"
        val JSON_MEDIA_TYPE = "application/json".toMediaType()
    }
}
