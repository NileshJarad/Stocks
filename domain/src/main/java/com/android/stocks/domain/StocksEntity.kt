package com.android.stocks.domain


data class StocksEntity(
    val avgPrice: String,
    val close: Double,
    val cncUsedQuantity: Int,
    val collateralQty: Int,
    val collateralType: String,
    val collateralUpdateQty: Int,
    val companyName: String,
    val haircut: Double,
    val holdingsUpdateQty: Int,
    val isin: String,
    val ltp: Double,
    val product: String,
    val quantity: Int,
    val symbol: String,
    val t1HoldingQty: Int,
    val tokenBse: String,
    val tokenNse: String,
    val withheldCollateralQty: Int,
    val withheldHoldingQty: Int
)
