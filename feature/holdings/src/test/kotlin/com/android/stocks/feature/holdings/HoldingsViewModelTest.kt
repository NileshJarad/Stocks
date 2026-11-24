package com.android.stocks.feature.holdings

import com.android.stocks.domain.model.Stock
import com.android.stocks.domain.usecase.CalculateHoldingsSummaryUseCase
import com.android.stocks.domain.usecase.GetHoldingsUseCase
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class HoldingsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = FakeStocksRepository()

    private fun createViewModel() = HoldingsViewModel(
        getHoldings = GetHoldingsUseCase(repository),
        calculateSummary = CalculateHoldingsSummaryUseCase(),
    )

    @Test
    fun `loads holdings and exposes success state`() = runTest(mainDispatcherRule.testDispatcher) {
        repository.result = Result.success(listOf(stock()))

        val viewModel = createViewModel()
        advanceUntilIdle()

        val state = viewModel.uiState.value
        assertThat(state).isInstanceOf(HoldingsUiState.Success::class.java)
        state as HoldingsUiState.Success
        assertThat(state.holdings).hasSize(1)
        assertThat(state.holdings.first().symbol).isEqualTo("TCS")
        assertThat(state.summary.currentValue).isEqualTo(1200.0)
        assertThat(state.summary.totalInvestment).isEqualTo(1000.0)
        assertThat(state.summary.overallPnl).isEqualTo(200.0)
        assertThat(state.summary.todaysPnl).isEqualTo(100.0)
    }

    @Test
    fun `exposes empty state when there are no holdings`() = runTest(mainDispatcherRule.testDispatcher) {
        repository.result = Result.success(emptyList())

        val viewModel = createViewModel()
        advanceUntilIdle()

        assertThat(viewModel.uiState.value).isEqualTo(HoldingsUiState.Empty)
    }

    @Test
    fun `exposes error state with friendly message for network failure`() =
        runTest(mainDispatcherRule.testDispatcher) {
            repository.result = Result.failure(IOException("timeout"))

            val viewModel = createViewModel()
            advanceUntilIdle()

            val state = viewModel.uiState.value
            assertThat(state).isInstanceOf(HoldingsUiState.Error::class.java)
            state as HoldingsUiState.Error
            assertThat(state.message).contains("No internet connection")
        }

    @Test
    fun `retry reloads holdings after an error`() = runTest(mainDispatcherRule.testDispatcher) {
        repository.result = Result.failure(IOException("timeout"))
        val viewModel = createViewModel()
        advanceUntilIdle()
        assertThat(viewModel.uiState.value).isInstanceOf(HoldingsUiState.Error::class.java)

        repository.result = Result.success(listOf(stock()))
        viewModel.loadHoldings()
        advanceUntilIdle()

        assertThat(viewModel.uiState.value).isInstanceOf(HoldingsUiState.Success::class.java)
    }

    private fun stock() = Stock(
        symbol = "TCS",
        companyName = "Tata Consultancy Services",
        quantity = 10,
        avgPrice = 100.0,
        lastPrice = 120.0,
        previousClose = 110.0,
    )
}
