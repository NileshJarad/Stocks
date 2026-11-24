package com.android.stocks.feature.holdings

import com.android.stocks.domain.model.HoldingsSummary
import com.android.stocks.domain.model.Stock

internal fun HoldingsSummary.toUi(): HoldingsSummaryUi = HoldingsSummaryUi(
    currentValue = currentValue,
    totalInvestment = totalInvestment,
    todaysPnl = todaysPnl,
    overallPnl = overallPnl,
)

internal fun Stock.toUi(): HoldingUi = HoldingUi(
    symbol = symbol,
    companyName = companyName,
    quantity = quantity,
    ltp = lastPrice,
    pnl = pnl,
)
