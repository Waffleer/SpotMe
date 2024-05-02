package com.example.spotme.viewmodels

import android.icu.text.NumberFormat
import androidx.compose.runtime.produceState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.spotme.data.Debt
import com.example.spotme.data.PaymentType
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
import java.util.Date

/**
 * Stores SpotMeApp's UI state for details screen.
 *
 * @property profiles stores the list of profiles.
 */
enum class FilterType() {
    AMOUNT_HIGH,
    AMOUNT_LOW,
    SUBSTRING,
    NONE,
}
data class DetailsUiState (
    // Put State Values Here:
    //val filter_profiles: List<Profile> = listOf(),
    val currentProfileId: Long? = 0,
    val filterType: FilterType = FilterType.NONE,
    val filterProfiles: List<Profile> = listOf(),
    val subString: String = "",
    //val currentProfile: Profile = Profile(0,"","",0.0,listOf(),PaymentType.NONE, Date(0),false),
)

data class DetailsCurrentProfile(
    val currentProfile: Profile = Profile(0,"","",0.0,listOf(),PaymentType.NONE, Date(0),false),
    )

data class DetailsProfiles(
    val profiles: List<Profile> = listOf(),
)



//data class ProfileEntity (
//    val selectedProfile : ProfileWithEverything
//)


class DetailsViewModel (
    repo: RepositoryInterface
    ) : ViewModel() {
    private val _uiState = MutableStateFlow(DetailsUiState())

    val uiState: StateFlow<DetailsUiState> = _uiState.asStateFlow()

    /**
     * updates the filter type and then updates the displayed list
     * @param filter: FilterType? what to change it to
     * @param pro: List<Profile>? // nullable list of profiles, this is when the local profile list is not initilizeds from the repository call
     */
    fun setFilterType(filter: FilterType?, pro: List<Profile>? = null){
        //println("setFilter = $filter")
        if(filter != null){
            _uiState.update {currentState ->
                currentState.copy(
                    filterType = filter
                )
            }
        }

        var con: List<Profile> = listOf() //Decides what datasource to use
        if(pro == null){
            con = profilesFlow.value.profiles
        }
        else{
            con = pro
        }


        //println("filter = ${uiState.value.filterType}")

        var ret: List<Profile> = listOf()
        //println("con = $con")

        if(uiState.value.filterType == FilterType.AMOUNT_HIGH){
            ret = filter_profiles_debt_amount_high(con)
        }
        if(uiState.value.filterType == FilterType.AMOUNT_LOW){
            ret = filter_profiles_debt_amount_low(con)
        }
        if(uiState.value.filterType == FilterType.SUBSTRING){
            ret = filter_profiles_by_substring(con)
        }
        if(uiState.value.filterType == FilterType.NONE){
            ret = con.toList()
        }
        //println("ret = $ret")

        _uiState.update {currentState ->
            currentState.copy(
                filterProfiles = ret
            )
        }
    }

//    fun whatisfiltertype(){
//        println("filter = ${uiState.value.filterType}")
//    }

    /**
     * filters the display list by amount >
     * @param con: List<Profile> // what profile list to sort
     */
    private fun filter_profiles_debt_amount_high(con: List<Profile>): List<Profile>{
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = con.toMutableList()
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
        return sorted
    }
    /**
     * filters the display list by amount <
     * @param con: List<Profile> // what profile list to sort
     */

    private fun filter_profiles_debt_amount_low(con: List<Profile>): List<Profile>{
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = con.toMutableList()
        //println(profiles)
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
        return sorted
    }

    /**
     * TODO Not Implemented yet
     * filters the display list by substring for search bar
     * @param con: List<Profile> // what profile list to sort
     */
    private fun filter_profiles_by_substring(con: List<Profile>): List<Profile>{
        val sorted : MutableList<Profile> = mutableListOf()
        var profiles : MutableList<Profile> = con.toMutableList()


        return con
    }

    /**
     * updates currentProfile id for ui
     * @param profile: Profile
     */
    public fun setCurrentProfile(profile: Profile){
        //val profileWithEverything = StaticDataSource.profilesWithEverything[0]
        //val con: Profile = eProfileWithEverything_to_uProfile(profileWithEverything)
        _uiState.update {currentState ->
            currentState.copy(
                currentProfileId = profile.id
            )
        }
    }


    var profilesFlow: StateFlow<DetailsProfiles>
            = repo.getProfiles()
        .map{
            //println("Filter Profiles Stateflow")
            println(uiState.value.filterType)
            var con: MutableList<Profile> = mutableListOf()
            it.forEach {p ->
                con.add(eProfile_to_uProfile(p, null))
            }
            //println("ProfileFlow con = $con")
            setFilterType( null, con)
            DetailsProfiles(con)
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(DetailsViewModel.TIMEOUT_MILLIS),
            initialValue = DetailsProfiles(profiles = listOf())
        )


//    var CurrentProfile: StateFlow<DetailsUiState> //Stores State collected from database
//            = repo.getSpecificProfileWithEverything(uiState.value.currentProfileId)
//        .map { // convert to a flow of DatabaseUiState
//            val prof = eProfileWithEverything_to_uProfile(it)
//            DetailsUiState(currentProfile = prof)
//        }.stateIn(
//            // Convert Flow to StateFlow
//            scope = viewModelScope,
//            started = SharingStarted.WhileSubscribed(DetailsViewModel.TIMEOUT_MILLIS),
//            initialValue = DetailsUiState(currentProfile = Profile(0,"","",0.0,listOf(),PaymentType.NONE, Date(0),false))
//        )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}