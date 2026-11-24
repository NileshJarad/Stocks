package com.android.stocks.domain.usecase

import com.android.stocks.domain.model.HoldingsSummary
import com.android.stocks.domain.model.Stock
import javax.inject.Inject

class CalculateHoldingsSummaryUseCase @Inject constructor() {

    operator fun invoke(holdings: List<Stock>): HoldingsSummary {
        var currentValue = 0.0
        var totalInvestment = 0.0
        var todaysPnl = 0.0

        holdings.forEach { stock ->
            currentValue += stock.currentValue
            totalInvestment += stock.investedValue
            todaysPnl += stock.todaysPnl
        }

        return HoldingsSummary(
            currentValue = currentValue,
            totalInvestment = totalInvestment,
            todaysPnl = todaysPnl,
            overallPnl = currentValue - totalInvestment,
        )
    }
}
