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

data class TotalBalance(val totalBalance: Double = 0.0)

data class LargestCreditor(
    val largestCreditor: Profile
        = StaticDataSource.eProfiles[0]
)
data class LargestDebtor(
    val largestDebtor: Profile
        = StaticDataSource.eProfiles[0]
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

data class Everything(
    val profilesWithEverything: List<ProfileWithEverything> = listOf()
)

class SummaryViewModel(spotMeRepository: RepositoryInterface): ViewModel() {

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
            LargestCreditor(it?: StaticDataSource.eProfiles[0])
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
            initialValue = LargestCreditor()
        )

    var primaryDebtor: StateFlow<LargestDebtor>
            = spotMeRepository.getLargestDebtor()
        .map {
            LargestDebtor(it?: StaticDataSource.eProfiles[0])
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