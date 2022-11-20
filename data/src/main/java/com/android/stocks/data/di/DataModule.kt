package com.android.stocks.data.di

import com.android.stocks.data.StocksListRepositoryImpl
import com.android.stocks.data.network.RetrofitBuilder
import com.android.stocks.domain.StocksListRepository
import org.koin.dsl.bind
import org.koin.dsl.module

val datModule = module {
    single { RetrofitBuilder().apiService }
    single { StocksListRepositoryImpl(get()) } bind StocksListRepository::class
}