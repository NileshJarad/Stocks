package com.android.stocks.core.common

import java.text.NumberFormat
import java.util.Locale

private val indiaLocale: Locale = Locale.forLanguageTag("en-IN")

private val currencyFormat: NumberFormat = NumberFormat.getCurrencyInstance(indiaLocale)

private val amountFormat: NumberFormat = NumberFormat.getNumberInstance(indiaLocale).apply {
    minimumFractionDigits = 2
    maximumFractionDigits = 2
}

fun Double.toCurrencyString(): String = synchronized(currencyFormat) { currencyFormat.format(this) }

fun Double.toAmountString(): String = synchronized(amountFormat) { amountFormat.format(this) }
