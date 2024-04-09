package com.example.spotme

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.spotme.database.DataAccessObject
import com.example.spotme.database.LocalDatabase
import com.example.spotme.database.Profile
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.hamcrest.CoreMatchers
import org.hamcrest.MatcherAssert
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
class DBUnitTest {
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
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeProfile() = runTest {
        // Add Test Profile
        val profile = Profile(
            1,
            "Bob Ross",
            "Famous painter guy, I think",
            "Venmo",
            0.0,
            Date()
        )
        userDao.insertProfile(profile)

        // Get Profiles
        val profiles = userDao.getProfilesWithDebts()

        // Check assertion
        MatcherAssert.assertThat(profiles.first().first().profile, CoreMatchers.equalTo(profile))
    }
}