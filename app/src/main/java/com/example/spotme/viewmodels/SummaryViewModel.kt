package com.example.spotme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.database.Profile
import com.example.spotme.database.ProfileWithDebts
import com.example.spotme.database.RepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Data class that stores Database State Information.
 *
 * @param placeholder grug
 */
data class ProfilesWithDebtsState(
    val profilesWithDebts: List<ProfileWithDebts> = listOf(),
)
data class TotalBalance(val totalBalance: Double = 0.0)


/**
 * Creates a stateflow that is updated whenever
 * a change to the database is made.
 *
 * @param spotMeRepository a repository that implements RepositoryInterface.
 * @property databaseUiModel database stateflow.
 */
class SummaryViewModel(spotMeRepository: RepositoryInterface): ViewModel() {

    var profilesWithDebts: StateFlow<ProfilesWithDebtsState> //Stores State collected from database
            = spotMeRepository.getProfilesWithDebts() //TODO REPLACE getSandwich() with real repo DAO method
        .map { // convert to a flow of DatabaseUiState
            ProfilesWithDebtsState(it) //TODO replace 0.0 with total calculating function
        }.stateIn(
            // Convert Flow to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = ProfilesWithDebtsState()
        )

    var totalBalance: StateFlow<TotalBalance>
            = spotMeRepository.getTotalBalance()
        .map {
            TotalBalance(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TotalBalance()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}