package com.example.spotme.data

import com.example.spotme.database.ProfileWithEverything

fun eTransactions_to_uTransactions(eTrans: com.example.spotme.database.Transaction): Transaction{
    return Transaction(eTrans.transactionId, debtId = eTrans.f_debt_id, eTrans.amount, eTrans.description, eTrans.canceled, eTrans.createdDate)
}

fun uTransactions_to_eTransactions(uTransaction: Transaction, debtId: Long?): com.example.spotme.database.Transaction{
    return com.example.spotme.database.Transaction(uTransaction.id, debtId, uTransaction.createdDate, uTransaction.amount, uTransaction.description, uTransaction.canceled )
}

fun eDebt_to_uDebt(eDebt: com.example.spotme.database.Debt, uTransactions: List<Transaction>): Debt {
    return Debt(eDebt.debtId, eDebt.f_profile_id, name = eDebt.name, description = eDebt.description, amount = eDebt.totalDebt, hidden = false, transactions = uTransactions, canceled = eDebt.canceled, createdDate = eDebt.createdDate )
}

fun uDebt_to_eDebt(uDebt: Debt): com.example.spotme.database.Debt{
    return com.example.spotme.database.Debt(uDebt.id, uDebt.userID, uDebt.name, uDebt.amount, uDebt.description, uDebt.canceled, uDebt.createdDate, )
}

fun eProfile_to_uProfile(eProfile: com.example.spotme.database.Profile, uDebts: List<Debt>?): Profile {
    var paymentType: PaymentType = PaymentType.NONE
    PaymentType.values().forEach{
        if(it.title == eProfile.paymentPreference){
            paymentType = it
        }
    }
    return Profile(eProfile.profileId, eProfile.name, eProfile.description, eProfile.totalDebt, uDebts, paymentType)
}

fun uProfile_to_eProfile(uProfile: Profile): com.example.spotme.database.Profile {
    return com.example.spotme.database.Profile(uProfile.id, uProfile.name, uProfile.description, uProfile.paymentPreference.toString(), uProfile.amount, uProfile.createdDate)
}

fun eProfileWithEverything_to_uProfile(pwe: ProfileWithEverything): Profile{
    var paymentType: PaymentType = PaymentType.NONE
    PaymentType.values().forEach{
        if(it.title == pwe.profile.paymentPreference){
            paymentType = it
        }
    }
    val debts: MutableList<Debt> = mutableListOf()
    pwe.debtsWithTransactions.forEach{d ->
        val trans: MutableList<Transaction> = mutableListOf()
        d.transactions.forEach { t ->
            trans.add(eTransactions_to_uTransactions(t))
        }
        debts.add(Debt(d.debt.debtId, d.debt.debtId, d.debt.name, d.debt.description, trans, d.debt.totalDebt, d.debt.canceled, d.debt.createdDate, false))
    }
    return Profile(pwe.profile.profileId, pwe.profile.name, pwe.profile.description, pwe.profile.totalDebt, debts, paymentType, pwe.profile.createdDate)
}