package com.android.stocks.data.di

import com.android.stocks.data.repository.StocksRepositoryImpl
import com.android.stocks.domain.repository.StocksRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    @Singleton
    abstract fun bindStocksRepository(impl: StocksRepositoryImpl): StocksRepository
}
