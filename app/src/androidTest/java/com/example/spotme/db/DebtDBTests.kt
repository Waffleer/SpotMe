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
class DebtDBTests {
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
        val debt = userDao.getDebts().first()
        assertTrue(debt.isEmpty())
    }

    @Test
    fun testAddDebt() = runBlocking {
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

        val retrivedDebt = userDao.getSpecificDebt(1)
        assertNotNull(retrivedDebt)
        assertEquals(retrivedDebt.first().name, "Walmart Trip")

        val retrivedProfile = userDao.getSpecificProfileWithEverything(11)
        assertNotNull(retrivedProfile)
        assertEquals(
            retrivedProfile.first().debtsWithTransactions.first().debt.name,
            "Walmart Trip"
        )
    }

    @Test
    fun testUpdateDebt() = runBlocking {
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
            2,
            1,
            "Walmart Trip",
            20.0,
            "Went to Walmart to buy junk for Apple",
            false,
            Date()
        )
        userDao.insertDebt(debt)

        // Update Debt
        val updatedDebt = Debt(
            2,
            1,
            "Walmart Trip 2",
            20.0,
            "Went to Walmart to buy junk for Apple",
            false,
            Date()
        )
        userDao.updateDebt(updatedDebt)

        val retrivedDebt = userDao.getSpecificDebt(2)
        assertNotNull(retrivedDebt)
        assertEquals(retrivedDebt.first().name, "Walmart Trip 2")

        val retrivedProfile = userDao.getSpecificProfileWithEverything(12)
        assertNotNull(retrivedProfile)
        assertEquals(
            retrivedProfile.first().debtsWithTransactions.first().debt.name,
            "Walmart Trip 2"
        )
    }

    @Test
    fun testDeleteDebt() = runBlocking {
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
        userDao.deleteDebt(debt)

        val retrivedDebt = userDao.getSpecificDebt(3)
        assertNull(retrivedDebt.firstOrNull())
    }
}