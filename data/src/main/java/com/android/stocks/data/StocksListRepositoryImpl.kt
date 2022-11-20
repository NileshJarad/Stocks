package com.android.stocks.data

import com.android.stocks.data.network.StockListApi
import com.android.stocks.domain.OutCome
import com.android.stocks.domain.StocksEntity
import com.android.stocks.domain.StocksListRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class StocksListRepositoryImpl(private val api: StockListApi) : StocksListRepository {
    private val message = "Failed to load the holdings."
    private val token = "6d0ad460-f600-47a7-b973-4a779ebbaeaf"

    override fun getStocksList() = flow {
        emit(OutCome.Loading)
        val data = api.getUsers(token)
        emit(
            OutCome.Success(data.stocks.map { it.toEntity() }.toList())
        )
    }.catch {
        emit(OutCome.Failed(message))
    }.flowOn(Dispatchers.IO)

}

fun StockResponse.toEntity() = StocksEntity(
    avgPrice = avgPrice,
    close = close,
    cncUsedQuantity = cncUsedQuantity,
    collateralQty = collateralQty,
    collateralType = collateralType,
    collateralUpdateQty = collateralUpdateQty,
    companyName = companyName,
    haircut = haircut,
    holdingsUpdateQty = holdingsUpdateQty,
    isin = isin,
    ltp = ltp,
    product = product,
    quantity = quantity,
    symbol = symbol,
    t1HoldingQty = t1HoldingQty,
    tokenBse = tokenBse,
    tokenNse = tokenNse,
    withheldCollateralQty = withheldCollateralQty,
    withheldHoldingQty = withheldHoldingQty
)
