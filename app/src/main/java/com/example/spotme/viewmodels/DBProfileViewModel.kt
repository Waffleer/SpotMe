package com.example.spotme.viewmodels



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

class DBProfileViewModel(spotMeRepository: RepositoryInterface): ViewModel() {
    val repo = spotMeRepository
    private val _uiState = MutableStateFlow(ProfileState())
    val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()


    fun removeProfileById(id: Long) {
        //Remove from db based off of id
    }

    suspend fun createProfile(name: String, description: String, paymentPreference: PaymentType) {
        val date = Date()
        val prof = Profile(null, name, description, paymentPreference.toString(), 0.0, date)
        val profId: Long? = repo.insertProfile(prof)
        if(profId == null){
            println("Profile ID is null")
            exitProcess(-1)
        }
        println("profId = :$profId")
        val debt = Debt(null, profId, "", 0.0, "", false, date)
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