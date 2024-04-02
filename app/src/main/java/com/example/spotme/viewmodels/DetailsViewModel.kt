package com.example.spotme.viewmodels

import android.icu.text.NumberFormat
import androidx.compose.runtime.produceState
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
    val profiles: List<Profile> = listOf(),
    val filter_profiles: List<Profile> = listOf(),
    val currentProfile: Profile? = null,
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
        //ToDo Change to Database implementation


        _uiState.value = DetailsUiState(
            profiles = profiles,
            filter_profiles = profiles,
        )
    }

    public fun filter_profiles_debt_amount_high(){
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = _uiState.value.profiles.toMutableList()
        while(!profiles.isEmpty()){
            var p: Profile? = null
            profiles.forEach {
                if (p == null) {
                    p = it
                }
                if (it.amount > p!!.amount) {
                    p = it
                }
            }
            p?.let { sorted.add(it) }
            profiles.remove(p)
        }
        _uiState.update {currentState ->
            currentState.copy(
                filter_profiles = sorted
            )
        }
    }

    public fun filter_profiles_debt_amount_low(){
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = _uiState.value.profiles.toMutableList()
        while(!profiles.isEmpty()){
            var p: Profile? = null
            profiles.forEach {
                    if (p == null) {
                        p = it
                    }
                    if (it.amount < p!!.amount) {
                        p = it
                    }
            }
            p?.let { sorted.add(it) }
            profiles.remove(p)
        }
        _uiState.update {currentState ->
            currentState.copy(
                filter_profiles = sorted
            )
        }
    }

    public fun setCurrentProfile(profile: Profile?){
        _uiState.update {currentState ->
            currentState.copy(
                currentProfile = profile
            )
        }
    }
}