package com.android.stocks.data.network

import com.android.stocks.data.StocksListResponse
import retrofit2.http.GET
import retrofit2.http.Path

interface StockListApi {
    @GET("v3/{token}")
    suspend fun getUsers(@Path("token") token: String): StocksListResponse
}