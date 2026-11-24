package com.android.stocks.feature.holdings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.stocks.domain.usecase.CalculateHoldingsSummaryUseCase
import com.android.stocks.domain.usecase.GetHoldingsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HoldingsViewModel @Inject constructor(
    private val getHoldings: GetHoldingsUseCase,
    private val calculateSummary: CalculateHoldingsSummaryUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow<HoldingsUiState>(HoldingsUiState.Loading)
    val uiState: StateFlow<HoldingsUiState> = _uiState.asStateFlow()

    private val _events = Channel<HoldingsEvent>(Channel.BUFFERED)
    val events: Flow<HoldingsEvent> = _events.receiveAsFlow()

    init {
        loadHoldings()
    }

    fun loadHoldings() {
        viewModelScope.launch {
            _uiState.value = HoldingsUiState.Loading
            getHoldings()
                .onSuccess { stocks ->
                    _uiState.value = if (stocks.isEmpty()) {
                        HoldingsUiState.Empty
                    } else {
                        HoldingsUiState.Success(
                            summary = calculateSummary(stocks).toUi(),
                            holdings = stocks.map { it.toUi() },
                        )
                    }
                }
                .onFailure { throwable ->
                    val message = throwable.toUserMessage()
                    _uiState.value = HoldingsUiState.Error(message)
                    _events.send(HoldingsEvent.ShowMessage(message))
                }
        }
    }

    private fun Throwable.toUserMessage(): String = when (this) {
        is IOException -> "No internet connection. Please try again."
        else -> message ?: "Failed to load the holdings."
    }
}
