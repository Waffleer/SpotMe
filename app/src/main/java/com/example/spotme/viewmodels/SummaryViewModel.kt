package com.example.spotme.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.data.StaticDataSource
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.RepositoryInterface
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

/**
 * Retains the account's total balance
 */
data class TotalBalance(val totalBalance: Double = 0.0)

/**
 * Contains the profile of the guy who you owe the most money to.
 */
data class LargestCreditor(
    val largestCreditor: Profile
        = StaticDataSource.criticalProfileDefaults[1]
)

/**
 * Contains the profile of the guy who owes you the most money.
 */
data class LargestDebtor(
    val largestDebtor: Profile
        = StaticDataSource.criticalProfileDefaults[0]
)

/**
 * Contains the oldest debt
 * (not in use currently)
 */
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
 * Contains every profile with every debt and transaction.
 */
data class Everything(
    val profilesWithEverything: List<ProfileWithEverything> = listOf()
)

/**
 * Extracts all necessary information from the database and stores them as stateflow.
 * @param spotMeRepository the project's repository.
 */
class SummaryViewModel(spotMeRepository: RepositoryInterface): ViewModel() {

    /** The account's total balance */
    var totalBalance: StateFlow<TotalBalance>
            = spotMeRepository.getTotalBalance()
        .map {
            TotalBalance(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = TotalBalance()
        )

    /** The guy to whom you owe the most money */
    var primaryCreditor: StateFlow<LargestCreditor>
            = spotMeRepository.getLargestCreditor()
        .map {
            LargestCreditor(it?: StaticDataSource.criticalProfileDefaults[1])
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LargestCreditor()
        )

    /** The guy who owes you the most money */
    var primaryDebtor: StateFlow<LargestDebtor>
            = spotMeRepository.getLargestDebtor()
        .map {
            LargestDebtor(it?: StaticDataSource.criticalProfileDefaults[0])
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LargestDebtor()
        )

    /** The oldest debt */
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

    /** All profiles, all debts, and all transactions */
    var everything: StateFlow<Everything>
        = spotMeRepository.getEverything()
        .map {
            Everything(it)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = Everything()
        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}