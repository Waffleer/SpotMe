package com.example.spotme.viewmodels

import android.icu.text.NumberFormat
import androidx.lifecycle.ViewModel
import com.example.spotme.data.Profile
import com.example.spotme.data.StaticDataSource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * Stores SpotMeApp's UI state for details screen.
 *
 * @property profiles stores the list of profiles.
 */
data class DetailsUiState (
    // Put State Values Here:
    val profiles: List<Profile> = listOf()
)

class DetailsViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        //Will get profiles from db with desired information
        //For now im just taking from the StaticDataSource
        val profiles = StaticDataSource.profiles
        _uiState.value = DetailsUiState(
            profiles = profiles
        )
    }

}