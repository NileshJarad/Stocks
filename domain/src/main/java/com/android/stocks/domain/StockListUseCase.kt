package com.android.stocks.domain

import kotlinx.coroutines.flow.Flow

interface StockListUseCase {
    fun getHoldings(): Flow<OutCome<List<StocksEntity>>>
    fun getPnl(currentValue: Double, investmentValue: Double): Double
    fun getCurrentValue(ltp: Double, qty: Int): Double
    fun getInvestmentValue(avgPrice: Double, qty: Int): Double
    fun getTodayPnl(close: Double, ltp: Double, quantity: Int): Double
}

class StockListUseCaseImpl(private val repository: StocksListRepository) : StockListUseCase {
    override fun getHoldings() = repository.getStocksList()
    override fun getPnl(currentValue: Double, investmentValue: Double) =
        currentValue - investmentValue


    override fun getCurrentValue(ltp: Double, qty: Int) = ltp * qty


    /**
     * Using formula as (avgPrice * qty)
     *
     * but in assessment PDF its mentioned
     *
     * 6. Investment value (Individual item) = Avg. Price - Quantity
     */
    override fun getInvestmentValue(avgPrice: Double, qty: Int) = avgPrice * qty
    override fun getTodayPnl(close: Double, ltp: Double, quantity: Int) = (close - ltp) * quantity

}