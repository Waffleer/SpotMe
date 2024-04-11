package com.example.spotme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.room.PrimaryKey
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import com.example.spotme.database.ProfileDebtTuple
import com.example.spotme.database.ProfileWithDebts
import com.example.spotme.database.RepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import java.util.Date

/**
 * Data class that stores Database State Information.
 * @param placeholder grug
 */
data class ProfilesWithDebtsState(
    val profilesWithDebts: List<ProfileWithDebts> = listOf(),
)
data class TotalBalance(val totalBalance: Double = 0.0)

data class LargestCreditor(
    val largestCreditor: ProfileDebtTuple
        = ProfileDebtTuple(0,"placeholder",0.0)
)
data class LargestDebtor(
    val largestDebtor: ProfileDebtTuple
        = ProfileDebtTuple(0,"placeholder",0.0)
)

data class OldestDebt(
    val oldestDebt: Debt = Debt(
            debtId = 0,
            f_profile_id = 0,
            name = "placeholder",
            totalDebt = 0.0,
            description = "placeholder description",
            canceled = false,
            createdDate = java.util.Date()
        )
)


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
            ProfilesWithDebtsState(it)
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

    var primaryCreditor: StateFlow<LargestCreditor>
            = spotMeRepository.getLargestCreditor()
        .map {
            LargestCreditor(it?: ProfileDebtTuple(0,"Namerson",0.0))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LargestCreditor()
        )

    var primaryDebtor: StateFlow<LargestDebtor>
            = spotMeRepository.getLargestCreditor()
        .map {
            LargestDebtor(it?: ProfileDebtTuple(0,"Namerson Jr.",0.0))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LargestDebtor()
        )

    var oldestDebt: StateFlow<OldestDebt>
        = spotMeRepository.getOldestDebt()
        .map{
            OldestDebt(it?: Debt(
                debtId = 0,
                f_profile_id = 0,
                name = "Debt Name",
                totalDebt = 0.0,
                description = "placeholder description",
                canceled = false,
                createdDate = java.util.Date()))
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = OldestDebt()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}