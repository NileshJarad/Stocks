package com.android.stocks.feature.holdings.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.android.stocks.core.common.toCurrencyString
import com.android.stocks.core.designsystem.theme.LossRed
import com.android.stocks.core.designsystem.theme.ProfitGreen
import com.android.stocks.feature.holdings.HoldingUi
import com.android.stocks.feature.holdings.R

@Composable
fun HoldingRow(
    holding: HoldingUi,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = holding.symbol,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = holding.companyName,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Text(
                    text = "${stringResource(R.string.qty_label)}: ${holding.quantity}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "${stringResource(R.string.ltp_label)} ${holding.ltp.toCurrencyString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = "${stringResource(R.string.pnl_label)} ${holding.pnl.toCurrencyString()}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = pnlColor(holding.pnl),
                )
            }
        }
        HorizontalDivider()
    }
}

@Composable
private fun pnlColor(value: Double) = when {
    value > 0 -> ProfitGreen
    value < 0 -> LossRed
    else -> MaterialTheme.colorScheme.onSurface
}
