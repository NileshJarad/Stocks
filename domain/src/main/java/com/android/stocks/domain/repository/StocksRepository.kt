package com.android.stocks.domain.repository

import com.android.stocks.domain.model.Stock

interface StocksRepository {
    suspend fun getHoldings(): Result<List<Stock>>
}
