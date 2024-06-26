package com.example.spotme.viewmodels

import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.example.spotme.database.RepositoryInterface
import androidx.lifecycle.ViewModel
import com.example.spotme.data.PaymentType
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import kotlin.system.exitProcess

data class ProfileState(
    val ProfileId: Long = -1
)

/**
 * @property spotMeRepository : ReposityInterface // repo for running repository functions
 */
class DBProfileViewModel(spotMeRepository: RepositoryInterface): ViewModel() {
    val repo = spotMeRepository
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()

//    suspend fun removeProfileById(id: Long) {
//
//    }
    /**
     * @param pid: Long // the id of the profile that is being edited
     * @param amount: Double //amount the profile totaldebt will be changed to
     */
    suspend fun editProfileAmount(pid: Long, amount: Double){
        val prof: Profile = repo.getProfileByIdNonFlow(pid)
        repo.updateProfile(prof.copy(totalDebt = amount))
    }

    /**
     * @param did: Long // id of the debt
     * @param amount: Double // amount the totalDebt will be changed to
     */
    suspend fun editDebtAmount(did: Long, amount: Double){
        val debt: Debt = repo.getDebtByIdNonFlow(did)
        repo.updateDebt(debt.copy(totalDebt = amount))
    }

//    suspend fun editProfileCanceled(pid: Long, cancel: Boolean){
//
//    }

    /**
     * Edit a profile by id.
     * @param pid the profile ID
     * @param name the new name of the profile
     * @param description the new description of the profile
     * @param paymentPreference the new payment preference
     */
    suspend fun editProfile(pid: Long, name: String, description: String, paymentPreference: String) {
        val profile : Profile = repo.getProfileByIdNonFlow(pid)
        repo.updateProfile(
            profile.copy(
                name = name,
                description = description,
                paymentPreference = paymentPreference
            )
        )
    }
    /**
     * Creates a new profile in db
     * @param name the profile ID
     * @param description the new description of the profile
     * @param paymentPreference the new payment preference
     */
    suspend fun createProfile(name: String, description: String, paymentPreference: PaymentType) {
        val date = Date()
        val prof = Profile(null, name, description, paymentPreference.toString(), 0.0, date)
        val profId: Long? = repo.insertProfile(prof)
        if(profId == null){
            println("Profile ID is null")
            exitProcess(-1)
        }
        println("profId = :$profId")
        val debt = Debt(null, profId, "Primary Debt", 0.0, "", false, date)
        repo.insertDebt(debt)
        _uiState.update {currentState ->
            currentState.copy(
                ProfileId = profId
            )
        }
    }
    /**
     * Creates a new profile in db
     * @param name the profile ID
     * @param description the new description of the profile
     * @param paymentPreference the new payment preference
     */
    suspend fun createProfile(name: String, description: String, paymentPreference: String) {
        val date = Date()
        val prof = Profile(null, name, description, paymentPreference, 0.0, date)
        val profId: Long? = repo.insertProfile(prof)
        if(profId == null){
            println("Profile ID is null")
            exitProcess(-1)
        }
        println("profId = :$profId")
        val debt = Debt(null, profId, "Primary Debt", 0.0, "", false, date)
        repo.insertDebt(debt)
        _uiState.update {currentState ->
            currentState.copy(
                ProfileId = profId
            )
        }
    }
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}