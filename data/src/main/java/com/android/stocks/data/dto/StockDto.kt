package com.android.stocks.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class StockDto(
    @SerialName("avg_price") val avgPrice: String = "0",
    val close: Double = 0.0,
    @SerialName("company_name") val companyName: String = "",
    val ltp: Double = 0.0,
    val quantity: Int = 0,
    val symbol: String = "",
)
