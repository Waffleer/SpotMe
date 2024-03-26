package com.example.spotme.viewmodels

import android.icu.text.NumberFormat
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Stores SpotMeApp's UI state.
 *
 * @property subtotal the current price of the user's sub.
 * @property subType the type of sub that the user selected.
 * @property breadType the type of bread that the user selected.
 * @property selectedToppings a set of toppings recording the user's selection.
 */
data class LocalUiState (
    // Put State Values Here:
    val debtTotal: String = "$0.00" // placeholder
)

class SpotMeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(LocalUiState())
    val uiState: StateFlow<LocalUiState> = _uiState.asStateFlow()

    /* Example where you need to do two updates:
    fun addTopping(item: String) {
        _uiState.update {currentState ->
            currentState.copy(
                selectedToppings = uiState.value.selectedToppings.plus(item),
            )
        }
        _uiState.update {currentState ->
            currentState.copy(
                subtotal = calculateSubtotal(
                    uiState.value.subType,
                    uiState.value.selectedToppings.size
                )
            )
        }
    }*/

    /**
     * Simply resets the UI State
     */
    fun resetUiState(){
        _uiState.value = LocalUiState()
    }
}