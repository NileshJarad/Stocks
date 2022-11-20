package com.android.stocks

import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.stocks.domain.OutCome
import com.android.stocks.domain.StockListUseCase
import com.android.stocks.domain.StocksEntity
import kotlinx.coroutines.launch
import java.text.DecimalFormat

class HoldingFragmentViewModel(private val useCase: StockListUseCase) : ViewModel() {
    private var decimalFormat = DecimalFormat("#.00")
    private val _holdingsData = MutableLiveData<List<HoldingsUiData>>()
    val holdingLiveData: LiveData<List<HoldingsUiData>> = _holdingsData


    val currentValueData = ObservableField("")
    val totalInvestmentData = ObservableField("")
    val todayProfitLossData = ObservableField("")
    val profitLossData = ObservableField("")


    fun getHolding() {
        currentValueData.set("0.0")
        totalInvestmentData.set("0.0")
        todayProfitLossData.set("0.0")
        profitLossData.set("0.0")
        viewModelScope.launch {
            var tempCurrent = 0.0
            var tempInvestmentValue = 0.0
            var tempPnl = 0.0
            useCase.getHoldings().collect {
                when (it) {
                    is OutCome.Loading -> Log.d(TAG, "Loading data")
                    is OutCome.Success -> {
                        Log.d(TAG, "Loading data $it")
                        _holdingsData.value = it.data.map { mapIt ->

                            val currValue = useCase.getCurrentValue(mapIt.ltp, mapIt.quantity)
                            val investmentValue = useCase.getInvestmentValue(
                                mapIt.avgPrice.toDouble(), mapIt.quantity
                            )

                            val todayPnl =
                                useCase.getTodayPnl(mapIt.close, mapIt.ltp, mapIt.quantity)
                            tempPnl += todayPnl
                            tempCurrent += currValue
                            tempInvestmentValue += investmentValue



                            mapIt.toUiModel(
                                useCase.getPnl(currValue, investmentValue), decimalFormat
                            )
                        }.toList()
                        currentValueData.set(decimalFormat.format(tempCurrent))
                        totalInvestmentData.set(decimalFormat.format(tempInvestmentValue))
                        profitLossData.set(decimalFormat.format(tempCurrent - tempInvestmentValue))
                        todayProfitLossData.set(decimalFormat.format(tempPnl))
                    }
                    is OutCome.Failed -> Log.d(TAG, "Loading Failed")
                    else -> {}
                }
            }
        }
    }

    companion object {
        private const val TAG = "HoldingFragmentViewMode"
    }
}

fun StocksEntity.toUiModel(pnl: Double, decimalFormat: DecimalFormat) = HoldingsUiData(
    symbol = this.symbol,
    qty = this.quantity,
    ltp = decimalFormat.format(this.ltp),
    pnl = decimalFormat.format(pnl)
)