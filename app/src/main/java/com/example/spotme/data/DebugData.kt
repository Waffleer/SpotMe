package com.example.spotme.data

import com.example.spotme.database.Debt
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Profile
import com.example.spotme.database.Transaction
import java.util.Date
import java.util.concurrent.Executors

object DebugData {
    val profiles = listOf(
        Profile(
            1,
            "Apple",
            "Love apples",
            PaymentType.NONE.toString(),
            20.0,
            Date()
        ),
        Profile(
            2,
            "Bob",
            "Hates apples",
            PaymentType.VENMO.toString(),
            -10.0,
            Date()
        ),
        Profile(
            3,
            "Carlos",
            "Indifferent to apples",
            PaymentType.PAYPAL.toString(),
            20.0,
            Date()
        )
    )

    val debts = listOf(
        Debt(
            1,
            1,
            "Walmart Trip",
            20.0,
            "Went to Walmart to buy junk for Apple",
            false,
            Date()
        ),
        Debt(
            2,
            2,
            "Target Trip",
            -10.0,
            "Bob bought me junk from target",
            false,
            Date()
        ),
        Debt(
            3,
            3,
            "Gas Station Trip",
            20.0,
            "Bought gas for carlos",
            false,
            Date()

        )
    )

    val transactions = listOf(
        Transaction(
            1,
            1,
            Date(),
            10.0,
            "Box of apples",
            false
        ),
        Transaction(
            2,
            1,
            Date(),
            10.0,
            "Container of apple sauce",
            false
        ),
        Transaction(
            3,
            2,
            Date(),
            -20.0,
            "Box of oranges",
            false
        ),
        Transaction(
            4,
            2,
            Date(),
            10.0,
            "Pack of orange juice",
            false
        ),
        Transaction(
            5,
            3,
            Date(),
            20.0,
            "Gasoline",
            false
        )
    )

    /**
     * Resets the db to initial debug data
     */
    fun resetInitialData(db: LocalDatabase) {

        // Run on a background thread
        Executors.newSingleThreadExecutor().execute {

            // Get the database
            val dao = db.getDao()

            // Force a database call to ensure it's open
            dao.getEverything()

            // Clear the database
            db.clearAllTables()

            // Append debug data
            profiles.forEach { dao.insertProfile(it) }
            debts.forEach { dao.insertDebt(it) }
            transactions.forEach { dao.insertTransaction(it) }
        }
    }
}