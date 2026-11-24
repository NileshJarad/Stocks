package com.android.stocks.domain.usecase

import com.android.stocks.domain.model.Stock
import org.junit.Assert.assertEquals
import org.junit.Test

class CalculateHoldingsSummaryUseCaseTest {

    private val useCase = CalculateHoldingsSummaryUseCase()

    @Test
    fun `computes summary across holdings`() {
        val holdings = listOf(
            stock(symbol = "TCS", quantity = 10, avgPrice = 100.0, lastPrice = 120.0, previousClose = 110.0),
            stock(symbol = "INFY", quantity = 5, avgPrice = 200.0, lastPrice = 180.0, previousClose = 190.0),
        )

        val summary = useCase(holdings)

        assertEquals(2100.0, summary.currentValue, 0.0001)
        assertEquals(2000.0, summary.totalInvestment, 0.0001)
        assertEquals(100.0, summary.overallPnl, 0.0001)
    }

    @Test
    fun `todays pnl is positive when price rises above previous close`() {
        val holdings = listOf(
            stock(quantity = 10, lastPrice = 120.0, previousClose = 100.0),
        )

        val summary = useCase(holdings)

        assertEquals(200.0, summary.todaysPnl, 0.0001)
    }

    @Test
    fun `todays pnl is negative when price falls below previous close`() {
        val holdings = listOf(
            stock(quantity = 10, lastPrice = 90.0, previousClose = 100.0),
        )

        val summary = useCase(holdings)

        assertEquals(-100.0, summary.todaysPnl, 0.0001)
    }

    @Test
    fun `empty holdings produce a zero summary`() {
        val summary = useCase(emptyList())

        assertEquals(0.0, summary.currentValue, 0.0001)
        assertEquals(0.0, summary.totalInvestment, 0.0001)
        assertEquals(0.0, summary.todaysPnl, 0.0001)
        assertEquals(0.0, summary.overallPnl, 0.0001)
    }

    private fun stock(
        symbol: String = "TCS",
        quantity: Int = 1,
        avgPrice: Double = 100.0,
        lastPrice: Double = 100.0,
        previousClose: Double = 100.0,
    ) = Stock(
        symbol = symbol,
        companyName = symbol,
        quantity = quantity,
        avgPrice = avgPrice,
        lastPrice = lastPrice,
        previousClose = previousClose,
    )
}
