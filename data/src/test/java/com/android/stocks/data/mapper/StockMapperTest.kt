package com.android.stocks.data.mapper

import com.android.stocks.data.dto.StockDto
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StockMapperTest {

    @Test
    fun `maps dto fields to domain model`() {
        val dto = StockDto(
            avgPrice = "123.45",
            close = 120.0,
            companyName = "Tata Consultancy Services",
            ltp = 130.5,
            quantity = 10,
            symbol = "TCS",
        )

        val stock = dto.toDomain()

        assertThat(stock.symbol).isEqualTo("TCS")
        assertThat(stock.companyName).isEqualTo("Tata Consultancy Services")
        assertThat(stock.quantity).isEqualTo(10)
        assertThat(stock.avgPrice).isEqualTo(123.45)
        assertThat(stock.lastPrice).isEqualTo(130.5)
        assertThat(stock.previousClose).isEqualTo(120.0)
    }

    @Test
    fun `falls back to zero when avg price is not numeric`() {
        val dto = StockDto(avgPrice = "N/A")

        val stock = dto.toDomain()

        assertThat(stock.avgPrice).isEqualTo(0.0)
    }
}
