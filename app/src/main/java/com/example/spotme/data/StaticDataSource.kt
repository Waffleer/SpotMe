package com.example.spotme.data

object StaticDataSource {



    val transactions: List<Transaction> = listOf(
        Transaction(1,100.0,"Description of the transaction1", false),
        Transaction(2,-200.0,"Description of the transaction2", false),
        Transaction(3,5.0,"Description of the transaction3", false),



    )
    val debts: List<Debt> = listOf(
        Debt(1,1, listOf(this.transactions[0],this.transactions[1],this.transactions[2]))



    )
    val persons: List<Person> = listOf(
        Person(1,"Name1","Biography1","Namason", debts = listOf(this.debts[0]), paymentPreference = (PaymentType.NONE))



    )
}