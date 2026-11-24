package com.android.stocks.domain.usecase

import com.android.stocks.domain.model.Stock
import com.android.stocks.domain.repository.StocksRepository
import javax.inject.Inject

class GetHoldingsUseCase @Inject constructor(
    private val repository: StocksRepository,
) {
    suspend operator fun invoke(): Result<List<Stock>> = repository.getHoldings()
}
