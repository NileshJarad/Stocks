package com.android.stocks.domain

import kotlinx.coroutines.flow.Flow

interface StocksListRepository {
    fun getStocksList(): Flow<OutCome<List<StocksEntity>>>
}