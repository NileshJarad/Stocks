package com.android.stocks.core.common

import org.junit.Assert.assertEquals
import org.junit.Test

class MoneyTest {

    @Test
    fun `toAmountString formats with two decimals`() {
        assertEquals("12,345.68", 12345.678.toAmountString())
    }

    @Test
    fun `toAmountString keeps two decimals for whole numbers`() {
        assertEquals("100.00", 100.0.toAmountString())
    }

    @Test
    fun `toCurrencyString contains rupee symbol`() {
        val formatted = 100.0.toCurrencyString()
        assertEquals(true, formatted.contains("\u20B9"))
    }
}
