package com.example.spotme.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.database.DataAccessObject
import com.example.spotme.database.Debt
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Profile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

/**
 * Unit test to test the functionality of SpotMe's Room DB using `LocalDatabase`
 */
@RunWith(AndroidJUnit4::class)
class TransactionDBTests {
    private lateinit var userDao: DataAccessObject
    private lateinit var db: LocalDatabase

    @Before
    fun createDB() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, LocalDatabase::class.java
        ).build()
        userDao = db.getDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDB() {
        db.close()
    }

    @Test
    fun testDatabaseInitialized() {
        assertNotNull(db)
        assertNotNull(userDao)
    }

    @Test
    fun testDatabaseEmpty() = runBlocking {
        val transactions = userDao.getTransactions().first()
        assertTrue(transactions.isEmpty())
    }

    @Test
    fun testAddTransactions() = runBlocking {
        // Add Profile
        val profile = Profile(
            1,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        // Add Debt
        val debt = Debt(
            1,
            1,
            "Walmart Trip",
            20.0,
            "Went to Walmart to buy junk for Apple",
            false,
            Date()
        )
        userDao.insertDebt(debt)

        // Add Transaction
        val transaction = com.example.spotme.database.Transaction(
            1,
            1,
            Date(),
            10.0,
            "Box of apples",
            false
        )
        userDao.insertTransaction(transaction)

        val retrivedTransaction = userDao.getSpecificTransaction(1)
        assertNotNull(retrivedTransaction)
        assertEquals(retrivedTransaction.first().description, "Box of apples")

        val retrivedProfile = userDao.getSpecificProfileWithEverything(1)
        val profileTransactions = retrivedProfile.first().debtsWithTransactions.first().transactions
        assertNotNull(retrivedProfile)
        assertEquals(profileTransactions.first().description, "Box of apples")
    }

    @Test
    fun testUpdateTransaction() = runBlocking {
        // Add Profile
        val profile = Profile(
            1,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        // Add Debt
        val debt = Debt(
            1,
            1,
            "Walmart Trip",
            20.0,
            "Went to Walmart to buy junk for Apple",
            false,
            Date()
        )
        userDao.insertDebt(debt)

        // Add Transaction
        val transaction = com.example.spotme.database.Transaction(
            1,
            1,
            Date(),
            10.0,
            "Box of apples",
            false
        )
        userDao.insertTransaction(transaction)

        // Update Transaction
        val updatedTransaction = com.example.spotme.database.Transaction(
            1,
            1,
            Date(),
            20.0,
            "Box of apples",
            false
        )
        userDao.updateTransaction(updatedTransaction)

        val retrivedTransaction = userDao.getSpecificTransaction(1)
        assertNotNull(retrivedTransaction)
        assertEquals(retrivedTransaction.first().amount, 20.0, 0.0)

        val retrivedProfile = userDao.getSpecificProfileWithEverything(1)
        val profileTransactions = retrivedProfile.first().debtsWithTransactions.first().transactions
        assertNotNull(retrivedProfile)
        assertEquals(profileTransactions.first().amount, 20.0, 0.0)
    }

    @Test
    fun testDeleteTransaction() = runBlocking {
        val transaction = com.example.spotme.database.Transaction(
            1,
            1,
            Date(),
            10.0,
            "Box of apples",
            false
        )
        userDao.insertTransaction(transaction)
        userDao.deleteTransaction(transaction)

        val retrivedTransaction = userDao.getSpecificTransaction(1)
        assertNull(retrivedTransaction.firstOrNull())
    }
}