package com.example.spotme.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import kotlinx.coroutines.flow.Flow


/**
 * Data Access object
 */
@Dao
interface DataAccessObject { // TODO currently setup for sandwiches. Change that.

    @Transaction // Get ALL profiles with EVERYTHING
    @Query("SELECT * FROM Profile")
    fun getEverything(): Flow<List<ProfileWithEverything>>

    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    fun getSpecificProfileWithEverything(profileId: Long?) : Flow<ProfileWithEverything>

    @Query("SELECT SUM(totalDebt) FROM Profile")
    fun getTotalBalance(): Flow<Double>

    @Query("SELECT profileId, name, totalDebt FROM Profile ORDER BY totalDebt DESC LIMIT 1")
    fun getLargestDebtor(): Flow<ProfileDebtTuple>

    @Query("SELECT profileId, name, totalDebt FROM Profile ORDER BY totalDebt LIMIT 1")
    fun getLargestCreditor(): Flow<ProfileDebtTuple>

    @Query("SELECT * FROM Debt ORDER BY createdDate LIMIT 1")
    fun getOldestDebt(): Flow<Debt>

    // <--- Get Specific Entity --->
    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    fun getSpecificProfile(profileId: Long?): Flow<Profile>

    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    fun getSpecificDebt(debtId: Long?): Flow<Debt>

    @Query("SELECT * FROM 'Transaction' WHERE transactionId = :transactionId")
    fun getSpecificTransaction(transactionId: Long?): Flow<com.example.spotme.database.Transaction>

    // <--- Get One To Many Relationships --->
    @Transaction // Get ALL Profiles with their Debts
    @Query("SELECT * FROM Profile")
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>

    @Transaction // Get a particular debt with all of it's transactions.
    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions>

    /**
     * Inserts a new profile into the database.
     * @param profile profile to be added
     */
    @Insert
    fun insertProfile(
        profile: Profile
    )
    /**
     * Inserts a new Debt into the database.
     * @param debt Debt to be added
     */
    @Insert
    suspend fun insertDebt(
            debt: Debt
    )

    /**
     * Inserts a new Transaction into the database.
     * @param transaction Transaction to be added
     */
    @Insert
    suspend fun insertTransaction(
        transaction: com.example.spotme.database.Transaction
    )
}