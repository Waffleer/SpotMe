package com.example.spotme.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters


/**
 * A database singleton for storing previously ordered subs.
 * Uses the Sandwich entity/table.
 * Uses the DateConverter class to convert dates into a storable format.
 *
 * @property SandwichDao DAO object used to provide access to stored data.
 */
@Database(entities = [Sandwich::class], version = 1) //TODO update entities
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
                    "subshop_db" //TODO change to "spotme_db"
                ).build()
            }
            return instance as LocalDatabase
        }
    }
}