package com.example.spotme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.database.Sandwich
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Data class that stores Database State Information.
 *
 * @param placeholder grug
 */
data class DatabaseUiState( //TODO add stuff to here
    val placeholder: List<Sandwich> = listOf(), // used to be called subHistory btw
    val totalBalance: Double
)

/**
 * Creates a stateflow that is updated whenever
 * a change to the database is made.
 *
 * @param spotMeRepository a repository that implements RepositoryInterface.
 * @property databaseUiModel database stateflow.
 */
class DatabaseViewModel(spotMeRepository: RepositoryInterface): ViewModel() {

    var databaseUiModel: StateFlow<DatabaseUiState> //Stores State collected from database
            = spotMeRepository.getSandwiches() //TODO REPLACE getSandwich() with real repo DAO method
        .map { // convert to a flow of DatabaseUiState
            DatabaseUiState(it, 0.0) //TODO replace 0.0 with total calculating function
        }.stateIn(
            // Convert Flow to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = DatabaseUiState(totalBalance = 0.0) //TODO replace 0.0 with total calculating function
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}