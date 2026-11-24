package com.android.stocks.domain.model

data class Stock(
    val symbol: String,
    val companyName: String,
    val quantity: Int,
    val avgPrice: Double,
    val lastPrice: Double,
    val previousClose: Double,
) {
    val investedValue: Double get() = avgPrice * quantity

    val currentValue: Double get() = lastPrice * quantity

    val pnl: Double get() = currentValue - investedValue

    val todaysPnl: Double get() = (lastPrice - previousClose) * quantity
}
