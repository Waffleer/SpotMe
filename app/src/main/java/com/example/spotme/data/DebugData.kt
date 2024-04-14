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
            40.0,
            Date()
        ),
        Profile(
            2,
            "Bob",
            "Hates apples",
            PaymentType.VENMO.toString(),
            0.0,
            Date()
        ),
        Profile(
            3,
            "Carlos",
            "Indifferent to apples",
            PaymentType.PAYPAL.toString(),
            -10.0,
            Date()
        )
    )

    val debts = listOf(
        Debt(
            1,
            1,
            "Walmart Trip",
            30.0,
            "Went to Walmart to buy junk",
            false,
            Date()
        ),
        Debt(
            2,
            1,
            "Target Trip",
            10.0,
            "Went to Target to buy junk",
            false,
            Date()
        ),
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
            20.0,
            "Box of oranges",
            false
        ),
        Transaction(
            3,
            1,
            Date(),
            30.0,
            "Box of chocolates",
            false
        ),
        Transaction(
            4,
            2,
            Date(),
            10.0,
            "Box of mangoes",
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