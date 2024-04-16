package com.example.spotme.viewmodels



import android.util.Log
import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.RepositoryInterface
import androidx.lifecycle.ViewModel
import androidx.room.PrimaryKey
import com.example.spotme.data.PaymentType
import com.example.spotme.data.StaticDataSource
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.util.Date
import kotlin.system.exitProcess


class dbProfileViewModel(spotMeRepository: RepositoryInterface): ViewModel() {
    val repo = spotMeRepository
    //private val _uiState = MutableStateFlow(ProfileState())
    //val uiState: StateFlow<ProfileState> = _uiState.asStateFlow()


    fun removeProfileById(id: Long) {
        //Remove from db based off of id
    }

    suspend fun createProfile(name: String, description: String, paymentPreference: PaymentType): Long {
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
        return profId
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}