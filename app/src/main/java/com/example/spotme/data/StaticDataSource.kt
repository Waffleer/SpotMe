package com.example.spotme.data

object StaticDataSource {
    // TODO: Move to `DebugData` class
    val transactions: List<Transaction> = listOf(
        Transaction(1, 100.0, "Description of the transaction1", false),
        Transaction(2, -200.0, "Description of the transaction2", false),
        Transaction(3, 5.0, "Description of the transaction3", false),
        Transaction(4, 10000.0, "A very large debt", false),
    )
    val debts: List<Debt> = listOf(
        Debt(
            1,
            1,
            "name1",
            "description1",
            listOf(this.transactions[0], this.transactions[1], this.transactions[2]),
            100.00
        ),
        Debt(2, 2, null, null, listOf(this.transactions[3]), 500.00),
        Debt(3, 3, null, null, listOf(this.transactions[2]), 500.00),
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

}