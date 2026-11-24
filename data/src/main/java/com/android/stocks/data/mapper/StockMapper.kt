package com.android.stocks.data.mapper

import com.android.stocks.data.dto.StockDto
import com.android.stocks.domain.model.Stock

fun StockDto.toDomain(): Stock = Stock(
    symbol = symbol,
    companyName = companyName,
    quantity = quantity,
    avgPrice = avgPrice.toDoubleOrNull() ?: 0.0,
    lastPrice = ltp,
    previousClose = close,
)
