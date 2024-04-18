package com.example.spotme.viewmodels



import com.example.spotme.database.RepositoryInterface
import androidx.lifecycle.ViewModel
import com.example.spotme.data.PaymentType
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.Transaction
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.util.Date
import kotlin.system.exitProcess

//data class TransactionState(
//    val ProfileId: Long = -1
//)

class DBTransactionViewModel(
    spotMeRepository: RepositoryInterface,
    updateProfile_: (pid: Long, amount: Double) -> Unit,
    updateDebt_: (did: Long, amount: Double) -> Unit,

): ViewModel() {
    val repo = spotMeRepository
    val updateProfile = updateProfile_
    val updateDebt = updateDebt_
    //private val _uiState = MutableStateFlow(TransactionState())
    //val uiState: StateFlow<TransactionState> = _uiState.asStateFlow()


    suspend fun removeTransactionById(tID: Long) {

    }

    suspend fun editTransactionCanceled(tid: Long, canceled: Boolean){

    }

    suspend fun createTransaction(profileID: Long, amount: Double, description: String) {


        var pwe: ProfileWithEverything? = null
        val profileWithEvery = repo.getSpecificProfileWithEverything(profileID)
        profileWithEvery.collect { pwe = it.copy()}
        if(pwe == null){
            println("ERROR = on function createTransaction profile did not properly return with given id $profileID")
            exitProcess(-1)
        }

        val debtid = pwe!!.debtsWithTransactions[0].debt.debtId

        // profileId to debt id, will need to search db

        val date = Date()
        val trans = Transaction(null, debtid, date, amount, description, false)
        val transId = repo.insertTransaction(trans)


        if (debtid != null) {
            updateDebt(debtid, amount + pwe!!.debtsWithTransactions[0].debt.totalDebt)
        }

        println("transID = $transId")

        updateProfile(profileID, amount + pwe!!.profile.totalDebt)

        //TODO re-compile debt and amount

        //_uiState.update {currentState ->
        //    currentState.copy(
        //
        //    )
        //}
    }

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}