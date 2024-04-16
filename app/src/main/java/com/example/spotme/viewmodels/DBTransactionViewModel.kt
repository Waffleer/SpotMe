package com.example.spotme.viewmodels



import com.example.spotme.database.RepositoryInterface
import androidx.lifecycle.ViewModel
import com.example.spotme.data.PaymentType
import com.example.spotme.database.Debt
import com.example.spotme.database.Profile
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
    editProfile_: (pid: Long, add_amount: Double) -> Unit,
): ViewModel() {
    val repo = spotMeRepository
    val editProfileAmount = editProfile_
    //private val _uiState = MutableStateFlow(TransactionState())
    //val uiState: StateFlow<TransactionState> = _uiState.asStateFlow()


    suspend fun removeTransactionById(tID: Long) {

    }

    suspend fun editTransactionCanceled(tid: Long, canceled: Boolean){

    }

    suspend fun createTransaction(profileID: Long, amount: Double, description: String) {

        // profileId to debt id, will need to search db
        val debtId: Long = 0
        val date = Date()
        val trans = Transaction(null, 0, date, amount, description, false)
        val transId = repo.insertTransaction(trans)
        println("transID = $transId")

        editProfileAmount(profileID, amount)

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