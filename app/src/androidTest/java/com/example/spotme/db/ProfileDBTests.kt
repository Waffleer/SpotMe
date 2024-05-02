package com.example.spotme.db

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.database.DataAccessObject
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Profile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

/**
 * Unit test to test the functionality of SpotMe's Room DB using `LocalDatabase`
 */
@RunWith(AndroidJUnit4::class)
class ProfileDBTests {
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
        val profiles = userDao.getProfiles().first()
        assertTrue(profiles.isEmpty())
    }

    @Test
    fun testAddProfile() = runBlocking {
        val profile = Profile(
            1,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        val retrivedProfile = userDao.getSpecificProfile(1)

        assertNotNull(retrivedProfile)
        assertEquals(retrivedProfile.first().name, "Bob Ross")
    }

    @Test
    fun testUpdateProfile() = runBlocking {
        val profile = Profile(
            2,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        // Update Profile
        val updatedProfile = Profile(
            2,
            "Bob Ross 2",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.updateProfile(updatedProfile)

        val retrivedProfile = userDao.getSpecificProfile(2)

        assertNotNull(retrivedProfile)
        assertEquals(retrivedProfile.first().name, "Bob Ross 2")
    }

    @Test
    fun testDeleteProfile() = runBlocking {
        val profile = Profile(
            3,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        userDao.deleteProfile(profile)

        val retrivedProfile = userDao.getSpecificProfile(3)
        assertNull(retrivedProfile.firstOrNull())
    }
}