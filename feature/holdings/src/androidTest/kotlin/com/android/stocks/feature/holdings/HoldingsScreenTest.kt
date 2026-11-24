package com.android.stocks.feature.holdings

import androidx.compose.material3.SnackbarHostState
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import org.junit.Rule
import org.junit.Test

class HoldingsScreenTest {

    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun successState_showsSummaryAndHoldings() {
        composeRule.setContent {
            HoldingsScreen(
                uiState = HoldingsUiState.Success(
                    summary = HoldingsSummaryUi(
                        currentValue = 1200.0,
                        totalInvestment = 1000.0,
                        todaysPnl = 100.0,
                        overallPnl = 200.0,
                    ),
                    holdings = listOf(
                        HoldingUi(
                            symbol = "TCS",
                            companyName = "Tata Consultancy Services",
                            quantity = 10,
                            ltp = 120.0,
                            pnl = 200.0,
                        ),
                    ),
                ),
                snackbarHostState = SnackbarHostState(),
                onRetry = {},
            )
        }

        composeRule.onNodeWithText("TCS").assertIsDisplayed()
        composeRule.onNodeWithText("Tata Consultancy Services").assertIsDisplayed()
    }

    @Test
    fun errorState_showsRetryButton() {
        composeRule.setContent {
            HoldingsScreen(
                uiState = HoldingsUiState.Error("Failed to load the holdings."),
                snackbarHostState = SnackbarHostState(),
                onRetry = {},
            )
        }

        composeRule.onNodeWithText("Retry").assertIsDisplayed()
    }
}
