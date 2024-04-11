package com.example.spotme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.spotme.data.DebugData


/**
 * A database singleton for storing previously ordered subs.
 * Uses the Sandwich entity/table.
 * Uses the DateConverter class to convert dates into a storable format.
 *
 * @property DataAccessObject DAO object used to provide access to stored data.
 */
@Database(
    entities = [Profile::class, Debt::class, Transaction::class],
    version = 5
) //TODO update entities
@TypeConverters(DateConverter::class)
abstract class LocalDatabase : RoomDatabase() {
    abstract fun getDao(): DataAccessObject

    /**
     * Provides a SubDatabase instance.
     * @returns instance as a SubDatabase.
     */
    companion object {
        private var instance: LocalDatabase? = null
        fun getInstance(context: Context): LocalDatabase {
            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    LocalDatabase::class.java,
                    "spotme_db"
                )
                    //.fallbackToDestructiveMigration() // Destroys DB to update schema
                    .build()

                // Uncomment to reset DB to default data
                instance?.resetInitialData()
            }
            return instance as LocalDatabase
        }
    }

    /**
     * Resets the db to initial debug data
     */
    private fun resetInitialData() {
        DebugData.resetInitialData(instance as LocalDatabase)
    }
}