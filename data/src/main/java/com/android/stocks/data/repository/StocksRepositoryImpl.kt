package com.android.stocks.data.repository

import com.android.stocks.core.common.di.IoDispatcher
import com.android.stocks.data.BuildConfig
import com.android.stocks.data.mapper.toDomain
import com.android.stocks.data.remote.StockApi
import com.android.stocks.domain.model.Stock
import com.android.stocks.domain.repository.StocksRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StocksRepositoryImpl @Inject constructor(
    private val api: StockApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : StocksRepository {

    override suspend fun getHoldings(): Result<List<Stock>> = withContext(ioDispatcher) {
        try {
            val response = api.getHoldings(BuildConfig.STOCKS_TOKEN)
            Result.success(response.data.map { it.toDomain() })
        } catch (cancellation: CancellationException) {
            throw cancellation
        } catch (throwable: Throwable) {
            Result.failure(throwable)
        }
    }
}
