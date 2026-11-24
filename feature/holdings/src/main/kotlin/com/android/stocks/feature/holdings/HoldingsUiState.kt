package com.android.stocks.feature.holdings

sealed interface HoldingsUiState {

    data object Loading : HoldingsUiState

    data object Empty : HoldingsUiState

    data class Error(val message: String) : HoldingsUiState

    data class Success(
        val summary: HoldingsSummaryUi,
        val holdings: List<HoldingUi>,
    ) : HoldingsUiState
}

data class HoldingsSummaryUi(
    val currentValue: Double,
    val totalInvestment: Double,
    val todaysPnl: Double,
    val overallPnl: Double,
)

data class HoldingUi(
    val symbol: String,
    val companyName: String,
    val quantity: Int,
    val ltp: Double,
    val pnl: Double,
)
