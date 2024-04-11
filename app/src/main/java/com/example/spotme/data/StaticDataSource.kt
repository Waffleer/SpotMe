package com.example.spotme.data

import com.example.spotme.database.ProfileWithEverything

object StaticDataSource {
    val transactions: List<Transaction> = listOf(
        Transaction(1,1,100.0,"Description of the transaction1", false),
        Transaction(2,1,-200.0,"Description of the transaction2", false),
        Transaction(3,2,5.0,"Description of the transaction3", false),
        Transaction(4,3,10000.0,"A very large debt", false),
    )
    val debts: List<Debt> = listOf(
        Debt(1,1, "name1", "description1", listOf(this.transactions[0],this.transactions[1]), 100.00, false),
        Debt(2,2, "", "",listOf(this.transactions[3]),500.00, false),
        Debt(3,3, "", "",listOf(this.transactions[2]),500.00, false),
    )
    val profiles: List<Profile> = listOf(
        Profile(1,"Namason","Biography1", debts = listOf(this.debts[0]), paymentPreference = (PaymentType.NONE), amount = 100.00),
        Profile(2,"Namason jr","Biography1", debts = listOf(this.debts[1]), paymentPreference = (PaymentType.NONE), amount = 200.00),
        Profile(3,"Jason","Jasons Bio", debts = listOf(this.debts[0]), paymentPreference = (PaymentType.NONE), amount = -100.00),
    )

    val profile: ProfileWithEverything = ProfileWithEverything(
        profile = com.example.spotme.database.Profile(1, "placeholder", "Placeholder","venmo",0.0,java.util.Date()),
        debtsWithTransactions = listOf()
    )



}