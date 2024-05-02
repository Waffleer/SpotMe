package com.example.spotme.viewmodels



import android.util.Log
import androidx.compose.animation.core.updateTransition
import androidx.compose.runtime.collectAsState
import com.example.spotme.database.RepositoryInterface
import androidx.lifecycle.ViewModel
import com.example.spotme.data.PaymentType
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.Transaction
import kotlinx.coroutines.flow.Flow
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


//    suspend fun removeTransactionById(tID: Long) {
//        repo.deleteTransaction(repo.getTransactionByIdNonFlow(tID))
//    }
//
    suspend fun editTransactionCanceled(pid: Long, tid: Long, canceled: Boolean){
        var trans: Transaction = repo.getTransactionByIdNonFlow(tid)
        repo.updateTransaction(trans.copy(canceled = canceled))

        var pwe: ProfileWithEverything = repo.getSpecificProfileWithEverythingNonFlow(pid)
        val debtid = pwe!!.debtsWithTransactions[0].debt.debtId
        var amount: Double = 0.0
        if(canceled){
            amount = trans.amount * -1
        }
        else{
            amount = trans.amount
        }
        if (debtid != null) {
            updateDebt(debtid, amount + pwe.debtsWithTransactions[0].debt.totalDebt)
        }
        updateProfile(pid, amount + pwe.profile.totalDebt)
    }


    /**
     * create a transaction by profileId
     * @param profileID: Long //what id to add the transaction to
     * @param amount: Double //the amount of the transaction
     * @param description: String //the description string
     */
    suspend fun createTransaction(profileID: Long, amount: Double, description: String) {
//        println("Creating Transaction")
//
        Log.d("j_transaction_viewmodel", "ProfileID: " + profileID.toString())

        var pwe: ProfileWithEverything = repo.getSpecificProfileWithEverythingNonFlow(profileID)

//        println("3")
//
//        println("Profileid = ${pwe.profile.profileId}")


        val debtid = pwe!!.debtsWithTransactions[0].debt.debtId
//        println("Debtid = ${debtid}")
        // profileId to debt id, will need to search db

        val date = Date()
        val trans = Transaction(null, debtid, date, amount, description, false)
        val transId = repo.insertTransaction(trans)
//        println("Transactionid = ${transId}")

        if (debtid != null) {
            updateDebt(debtid, amount + pwe.debtsWithTransactions[0].debt.totalDebt)
        }

//        println("transID = $transId")

        updateProfile(profileID, amount + pwe.profile.totalDebt)

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