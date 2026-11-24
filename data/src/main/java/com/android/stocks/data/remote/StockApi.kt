package com.android.stocks.data.remote

import com.android.stocks.data.dto.HoldingsResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

interface StockApi {
    @GET("v3/{token}")
    suspend fun getHoldings(@Path("token") token: String): HoldingsResponseDto
}
