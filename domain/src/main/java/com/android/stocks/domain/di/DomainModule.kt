package com.android.stocks.domain.di


import com.android.stocks.domain.StockListUseCase
import com.android.stocks.domain.StockListUseCaseImpl
import org.koin.dsl.bind
import org.koin.dsl.module

val domainModule = module {
    single { StockListUseCaseImpl(get()) } bind StockListUseCase::class
}