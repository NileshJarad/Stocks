package com.android.stocks.feature.holdings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.stocks.core.common.toCurrencyString
import com.android.stocks.core.designsystem.theme.LossRed
import com.android.stocks.core.designsystem.theme.ProfitGreen
import com.android.stocks.feature.holdings.HoldingsSummaryUi
import com.android.stocks.feature.holdings.R

@Composable
fun HoldingsSummaryCard(
    summary: HoldingsSummaryUi,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            SummaryRow(
                label = stringResource(R.string.current_value),
                value = summary.currentValue.toCurrencyString(),
            )
            SummaryRow(
                label = stringResource(R.string.total_investment),
                value = summary.totalInvestment.toCurrencyString(),
            )
            SummaryRow(
                label = stringResource(R.string.todays_profit_loss),
                value = summary.todaysPnl.toCurrencyString(),
                valueColor = pnlColor(summary.todaysPnl),
            )
            SummaryRow(
                label = stringResource(R.string.profit_loss),
                value = summary.overallPnl.toCurrencyString(),
                valueColor = pnlColor(summary.overallPnl),
            )
        }
    }
}

@Composable
private fun SummaryRow(
    label: String,
    value: String,
    valueColor: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.SemiBold,
            color = valueColor,
        )
    }
}

@Composable
private fun pnlColor(value: Double) = when {
    value > 0 -> ProfitGreen
    value < 0 -> LossRed
    else -> MaterialTheme.colorScheme.onPrimaryContainer
}
