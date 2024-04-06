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



    @Transaction // Get a particular debt with all of it's transactions.
    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    fun getDebtWithTransactions(debtId: Long?): Flow<List<DebtWithTransactions>>


    @Query("SELECT SUM(totalDebt) FROM Profile")
    fun getTotalBalance(): Flow<Double>

    @Query("SELECT profileId, name, totalDebt FROM Profile ORDER BY totalDebt DESC LIMIT 1")
    fun getLargestDebtor(): Flow<ProfileDebtTuple>

    @Query("SELECT profileId, name, totalDebt FROM Profile ORDER BY totalDebt LIMIT 1")
    fun getLargestCreditor(): Flow<ProfileDebtTuple>

    @Query("SELECT * FROM Profile Where profileId = :profileId")
    fun getSpecificProfile(profileId: Long?)

    /**
     * Inserts a new profile into the database.
     * @param profile profile to be added
     */
    @Insert
    suspend fun insertProfile(
        profile: Profile
    )
}