package com.android.stocks.domain.model

data class HoldingsSummary(
    val currentValue: Double,
    val totalInvestment: Double,
    val todaysPnl: Double,
    val overallPnl: Double,
)
