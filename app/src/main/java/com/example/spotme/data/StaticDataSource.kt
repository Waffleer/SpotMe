package com.example.spotme.data

object StaticDataSource {
    val transactions: List<Transaction> = listOf(
        Transaction(1,100.0,"Description of the transaction1", false),
        Transaction(2,-200.0,"Description of the transaction2", false),
        Transaction(3,5.0,"Description of the transaction3", false),
        Transaction(4,10000.0,"A very large debt", false),
    )
    val debts: List<Debt> = listOf(
        Debt(1,1, "name1", "description1", listOf(this.transactions[0],this.transactions[1],this.transactions[2])),
        Debt(2,2, null, null,listOf(this.transactions[3])),


        //Group Debts
        Debt(3, 1,"Camping Trip","", listOf()),
        Debt(4, 2,"Camping Trip","", listOf()),
        Debt(5, 3,"Camping Trip","", listOf()),
        Debt(6, -1, null, null, listOf()) //debt for the group

    )
    val profiles: List<Profile> = listOf(
        Profile(1,"Namason","Biography1", debts = listOf(this.debts[0]), paymentPreference = (PaymentType.NONE)),
        Profile(2,"Namason jr","Biography1", debts = listOf(this.debts[1]), paymentPreference = (PaymentType.NONE)),
        Profile(3,"Jason","Jasons Bio", debts = listOf(this.debts[3]), paymentPreference = (PaymentType.NONE)),
    )
//    val groups: List<Group> = listOf(
//        Group(1,"Camping Trip", "This is a test group for a fake camping trip", listOf(profiles[0],profiles[1],profiles[2]), debts[5], listOf(debts[2], debts[3], debts[4]))
//    )
}