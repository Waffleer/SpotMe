package com.example.spotme.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


/**
 * Data Access object
 */
@Dao
interface DataAccessObject { // TODO currently setup for sandwiches. Change that.
    /**
     * Gets a flow of a list of previously ordered sandwiches.
     * Contains an SQL Query: "SELECT * FROM sandwich"
     *//*
    @Query("SELECT * FROM sandwich")
    fun getAll(): Flow<List<Sandwich>>

    /**
     * Inserts a new sandwich into the database.
     * @param sub the Sandwich to be inserted.
     */
    @Insert
    suspend fun insertSandwich(
        sub: Sandwich
    )

    /**
     * Used to delete a sandwich.
     * @param sub the Sandwich to be deleted.
     */
    @Delete
    suspend fun deleteSandwich(
        sub: Sandwich
    )*/

    @Transaction // Get Profiles with their Debts
    @Query("SELECT * FROM Profile")
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>


    @Transaction // TODO modify to return a particular debt -< transaction pair
    @Query("SELECT * FROM Debt")
    fun getDebtWithTransactions(): Flow<List<DebtWithTransactions>>


    @Query("SELECT SUM(totalDebt) FROM Debt")
    fun getTotalBalance(): Flow<Double>

    /**
     * Inserts a new profile into the database.
     * @param profile profile to be added
     */
    @Insert
    suspend fun insertProfile(
        profile: Profile
    )
}