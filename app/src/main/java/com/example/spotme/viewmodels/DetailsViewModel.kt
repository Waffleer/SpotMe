package com.example.spotme.viewmodels

import android.icu.text.NumberFormat
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.data.Profile
import com.example.spotme.data.StaticDataSource
import com.example.spotme.database.RepositoryInterface
import com.example.spotme.data.eProfileWithEverything_to_uProfile
import com.example.spotme.data.eProfile_to_uProfile
import com.example.spotme.database.ProfileWithDebts
import com.example.spotme.database.ProfileWithEverything
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

/**
 * Stores SpotMeApp's UI state for details screen.
 *
 * @property profiles stores the list of profiles.
 */
data class DetailsUiState (
    // Put State Values Here:
    val filter_profiles: List<Profile> = listOf(),
    val currentProfile: Profile? = null,
    val currentProfileId: Long? = 0
)

data class ProfileEntity (
    val selectedProfile : ProfileWithEverything
)


class DetailsViewModel (
    //spotMeRepository: RepositoryInterface
    ) : ViewModel() {
    private var profiles: List<Profile> = listOf()
    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    init {
        initializeUIState()
    }

    private fun initializeUIState() {
        //Will get profiles from db with desired information
        //For now im just taking from the StaticDataSource
        val eProfiles: List<com.example.spotme.database.Profile> = StaticDataSource.eProfiles
        val con: MutableList<Profile> = mutableListOf()
        //ToDo Change to Database implementation
        eProfiles.forEach{
            con.add(
                eProfile_to_uProfile(it, null)
            )
        }
        profiles = con.toList()


        _uiState.value = DetailsUiState(
            filter_profiles = profiles,
        )
    }

    public fun filter_profiles_debt_amount_high(){
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = profiles.toMutableList()
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
        var profiles : MutableList<Profile> = profiles.toMutableList()
        println(profiles)
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
            p?.let {
                sorted.add(it)
                println(it)
            }
            profiles.remove(p)
        }
        _uiState.update {currentState ->
            currentState.copy(
                filter_profiles = sorted
            )
        }
    }

    public fun filter_profiles_by_substring(str: String){
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = profiles.toMutableList()
    }

    public fun setCurrentProfile(profile: Profile?){
        //TODO Search db and get ProfileWithEverything Based Off of profile id
        val profileWithEverything = StaticDataSource.profilesWithEverything[0]

        val con: Profile = eProfileWithEverything_to_uProfile(profileWithEverything)
        _uiState.update {currentState ->
            currentState.copy(
                currentProfile = con
            )
        }
    }

//    var profileWithDebts: StateFlow<ProfileEntity> //Stores State collected from database
//            = spotMeRepository.getSpecificProfileWithEverything(uiState.value.currentProfileId) //TODO REPLACE getSandwich() with real repo DAO method
//        .map { // convert to a flow of DatabaseUiState
//            ProfileEntity(it)
//        }.stateIn(
//            // Convert Flow to StateFlow
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(DetailsViewModel.TIMEOUT_MILLIS),
//            initialValue = ProfileEntity(StaticDataSource.profile)
//        )
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}