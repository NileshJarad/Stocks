package com.android.stocks.feature.holdings

sealed interface HoldingsEvent {
    data class ShowMessage(val message: String) : HoldingsEvent
}
