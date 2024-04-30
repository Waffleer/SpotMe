package com.example.spotme.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.data.StaticDataSource
import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.RepositoryInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/** Contains the currently selected profile with everything */
data class ProfileEntity(
    val profileWithEverything: ProfileWithEverything = StaticDataSource.profileWithEverything
)

/** Contains the ID of the currently selected profile */
data class ExpandedProfileUIState(
    val currentProfileId: Long = 0,
)

/**
 * The view model for the expanded profile screen as well as any other screen
 * that needs to be able to select or access the currently selected profile.
 */
class ExpandedProfileViewModel(spotMeRepository: RepositoryInterface): ViewModel() {
    val repo = spotMeRepository
    private val _uiState = MutableStateFlow(ExpandedProfileUIState())
    val uiState: StateFlow<ExpandedProfileUIState> = _uiState.asStateFlow()

    var profileWithEverything: StateFlow<ProfileEntity> //Stores State collected from database
            = spotMeRepository.getSpecificProfileWithEverything(uiState.value.currentProfileId)
        .map { // convert to a flow of DatabaseUiState
            Log.d("x_test", "test: " + it.profile.name)
            ProfileEntity(it?: StaticDataSource.profileWithEverything)
        }.stateIn(
            // Convert Flow to StateFlow
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(ExpandedProfileViewModel.TIMEOUT_MILLIS),
            initialValue = ProfileEntity()
        )

    fun setCurrentProfileId(id: Long) {
        _uiState.update { currentState ->
                currentState.copy(
                    currentProfileId = id
                )
        }
        Log.d("x_setCurrentProfile", "profileId: " + id)
        profileWithEverything = repo.getSpecificProfileWithEverything(id)
            .map { // convert to a flow of DatabaseUiState
                ProfileEntity(it?: StaticDataSource.profileWithEverything)
            }.stateIn(
                // Convert Flow to StateFlow
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(ExpandedProfileViewModel.TIMEOUT_MILLIS),
                initialValue = ProfileEntity()
            )
    }

    fun setToNewestProfile() {
        profileWithEverything = repo.getNewestProfile()
            .map { // convert to a flow of DatabaseUiState
                ProfileEntity(it?: StaticDataSource.profileWithEverything)
            }.stateIn(
                // Convert Flow to StateFlow
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(ExpandedProfileViewModel.TIMEOUT_MILLIS),
                initialValue = ProfileEntity()
            )
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}