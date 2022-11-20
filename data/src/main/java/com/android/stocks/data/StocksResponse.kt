package com.android.stocks.data

import com.google.gson.annotations.SerializedName


data class StocksListResponse(
    @SerializedName("client_id") val clientId: String,
    @SerializedName("data") val stocks: List<StockResponse>,
    val error: String,
    @SerializedName("response_type") val responseType: String,
    val timestamp: Long
)


data class StockResponse(
    @SerializedName("avg_price") val avgPrice: String,
    val close: Double,
    @SerializedName("cnc_used_quantity") val cncUsedQuantity: Int,
    @SerializedName("collateral_qty") val collateralQty: Int,
    @SerializedName("collateral_type") val collateralType: String,
    @SerializedName("collateral_update_qty") val collateralUpdateQty: Int,
    @SerializedName("company_name") val companyName: String,
    val haircut: Double,
    @SerializedName("holdings_update_qty") val holdingsUpdateQty: Int,
    val isin: String,
    val ltp: Double,
    val product: String,
    val quantity: Int,
    val symbol: String,
    @SerializedName("t1_holding_qty") val t1HoldingQty: Int,
    @SerializedName("token_bse") val tokenBse: String,
    @SerializedName("token_nse") val tokenNse: String,
    @SerializedName("withheld_collateral_qty") val withheldCollateralQty: Int,
    @SerializedName("withheld_holding_qty") val withheldHoldingQty: Int
)