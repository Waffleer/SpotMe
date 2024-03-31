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
data class SummaryUiState( //TODO add stuff to here
    val placeholder: List<ProfileWithDebts> = listOf(), // used to be called subHistory btw
    val totalBalance: Double
)

/**
 * Creates a stateflow that is updated whenever
 * a change to the database is made.
 *
 * @param spotMeRepository a repository that implements RepositoryInterface.
 * @property databaseUiModel database stateflow.
 */
class SummaryViewModel(spotMeRepository: RepositoryInterface): ViewModel() {
//
//    var summaryUiModel: StateFlow<SummaryUiState> //Stores State collected from database
//            = spotMeRepository.getProfileWithDebts() //TODO REPLACE getSandwich() with real repo DAO method
//        .map { // convert to a flow of DatabaseUiState
//            var balance: Double = 0.0 // Calculate total balance.
//            SummaryUiState(it, balance) //TODO replace 0.0 with total calculating function
//        }.stateIn(
//            // Convert Flow to StateFlow
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
//            initialValue = SummaryUiState(totalBalance = 0.0) //TODO replace 0.0 with total calculating function
//        )
//
//    companion object {
//        private const val TIMEOUT_MILLIS = 5_000L
//    }
}