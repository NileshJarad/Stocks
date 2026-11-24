package com.android.stocks.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class HoldingsResponseDto(
    @SerialName("client_id") val clientId: String = "",
    @SerialName("data") val data: List<StockDto> = emptyList(),
    val error: String = "",
    @SerialName("response_type") val responseType: String = "",
    val timestamp: Long = 0L,
)
