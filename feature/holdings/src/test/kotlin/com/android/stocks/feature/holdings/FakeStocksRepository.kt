package com.android.stocks.feature.holdings

import com.android.stocks.domain.model.Stock
import com.android.stocks.domain.repository.StocksRepository

class FakeStocksRepository : StocksRepository {

    var result: Result<List<Stock>> = Result.success(emptyList())

    override suspend fun getHoldings(): Result<List<Stock>> = result
}
