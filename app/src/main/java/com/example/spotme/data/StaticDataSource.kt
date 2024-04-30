package com.example.spotme.data

import com.example.spotme.database.ProfileWithEverything
import com.example.spotme.database.DebtWithTransactions
import java.util.Date


object StaticDataSource {
    // TODO: Move to `DebugData` class
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
        Profile(
            1,
            "Namason",
            "Biography1",
            debts = listOf(this.debts[0]),
            paymentPreference = (PaymentType.NONE),
            amount = 100.00
        ),
        Profile(
            2,
            "Namason jr",
            "Biography1",
            debts = listOf(this.debts[1]),
            paymentPreference = (PaymentType.NONE),
            amount = 200.00
        ),
        Profile(
            3,
            "Jason",
            "Jasons Bio",
            debts = listOf(this.debts[0]),
            paymentPreference = (PaymentType.NONE),
            amount = -100.00
        ),
    )

    val profileWithEverything: ProfileWithEverything = ProfileWithEverything(
        profile = com.example.spotme.database.Profile(1, "static-placeholder", "Placeholder","venmo",0.0,java.util.Date()),
        debtsWithTransactions = listOf()
    )

    val eProfiles: List<com.example.spotme.database.Profile> = listOf(
        com.example.spotme.database.Profile(1,"Nameason","Biography1","", 100.0, Date(1000)),
        com.example.spotme.database.Profile(2,"Namason jr","Biography2","paypal", 200.0, Date(0)),
        com.example.spotme.database.Profile(3,"Jason2","Jasons Bio","venmo",-100.0, Date(100000)),
        com.example.spotme.database.Profile(11,"No Debtors","There are no profiles that owe you money","NONE",0.0, Date()),
        com.example.spotme.database.Profile(12,"No Creditors","You don't owe money to any creditors","NONE",0.0, Date()),
    )

    //Only implemented profilesWithEverything for first profile (id = 1)
    val eDebts: List<com.example.spotme.database.Debt> = listOf(
        com.example.spotme.database.Debt(1,1,"Main",100.0,"Debt Des",false,Date(0))
    )
    val eTransactions: List<com.example.spotme.database.Transaction> = listOf(
        com.example.spotme.database.Transaction(1,1,Date(0),30.0,"", false),
        com.example.spotme.database.Transaction(2,1,Date(0),70.0, "",false)
    )
    val debtsWithTransactions_s: List<DebtWithTransactions> = listOf(
        DebtWithTransactions(this.eDebts[0], this.eTransactions)
    )
    val profilesWithEverything: List<ProfileWithEverything> = listOf(
        ProfileWithEverything(this.eProfiles[0],this.debtsWithTransactions_s)
    )
}