package com.example.spotme.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


/**
 * Data Access object
 */
@Dao
interface DataAccessObject { // TODO currently setup for sandwiches. Change that.

    /** Get ALL profiles with EVERYTHING. */
    @Transaction
    @Query("SELECT * FROM Profile")
    fun getEverything(): Flow<List<ProfileWithEverything>>

    /** Get all profiles. */
    @Query("SELECT * FROM Profile")
    fun getProfiles(): Flow<List<Profile>>

    /** Get a specific profile with all debts and transactions.
     * @param profileId the id of the profile you want to get */
    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    fun getSpecificProfileWithEverything(profileId: Long?): Flow<ProfileWithEverything>

    /** Get a specific profile with everything but as a non flow object.
     * @param profileId the id of the profile you want to get */
    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    suspend fun getSpecificProfileWithEverythingNonFlow(profileId: Long?): ProfileWithEverything

    /** Get the user's total balance. */
    @Query("SELECT SUM(totalDebt) FROM Profile")
    fun getTotalBalance(): Flow<Double>

    /** Get the the profile that the user is most in debt to. */
    @Query("SELECT * FROM Profile WHERE totalDebt > 0 ORDER BY totalDebt DESC LIMIT 1")
    fun getLargestDebtor(): Flow<Profile>

    /** Get the profile that is most in debt to the user. */
    @Query("SELECT * FROM Profile WHERE totalDebt < 0 ORDER BY totalDebt LIMIT 1")
    fun getLargestCreditor(): Flow<Profile>

    /** Get the oldest debt. */
    @Query("SELECT * FROM Debt ORDER BY createdDate LIMIT 1")
    fun getOldestDebt(): Flow<Debt>

    // <--- Get Specific Entity --->

    /** Get a specific profile.
     * @param profileId the ID of the profile you want to get */
    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    fun getSpecificProfile(profileId: Long?): Flow<Profile>


     /** Get a specific profile but as a non flow object.
     * @param profileId the ID of the profile you want to get */
    @Query("SELECT * FROM Profile WHERE profileId = :profileId")
    suspend fun getSpecificProfileNonFlow(profileId: Long?): Profile

    /** Get a specific debt object.
     * @param debtId the ID of the debt you want to get */
    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    fun getSpecificDebt(debtId: Long?): Flow<Debt>

    /** Get a specific debt but as a non flow object.
     * @param debtId the ID of the debt you want to get */
    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    suspend fun getSpecificDebtNonFlow(debtId: Long?): Debt

    /** Get a specific transaction.
     * @param transactionId the ID of the transaction you want to get */
    @Query("SELECT * FROM 'Transaction' WHERE transactionId = :transactionId")
    fun getSpecificTransaction(transactionId: Long?): Flow<com.example.spotme.database.Transaction>

    // <--- Get One To Many Relationships --->

    /** Get ALL profiles with their debts. */
    @Transaction // Get ALL Profiles with their Debts
    @Query("SELECT * FROM Profile")
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>

    /** Get a particular debt with all of it's transactions.
     * @param debtId the ID of the debt you want to get */
    @Transaction
    @Query("SELECT * FROM Debt WHERE debtId = :debtId")
    fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions>

    /**
     * Inserts a new profile into the database.
     * @param profile profile to be added
     */
    @Insert
    suspend fun insertProfile(
        profile: Profile
    ): Long?

    /**
     * Updates a profile in the database
     * @param profile the profile to be updated.
     */
    @Update
    suspend fun updateProfile(
        profile: Profile,
    )

    /**
     * Deletes a profile from the database
     * @param profile the profile to be deleted
     */
    @Delete
    suspend fun deleteProfile(
        profile: Profile,
    )



    /**
     * Inserts a new Debt into the database.
     * @param debt Debt to be added
     */
    @Insert
    suspend fun insertDebt(
        debt: Debt
    ): Long?

    /**
     * Updates a debt in the database.
     * @param debt the debt to be updated
     */
    @Update
    suspend fun updateDebt(
        debt: Debt
    )

    /**
     * Delete a debt from the database.
     * @param debt the debt to be deleted
     */
    @Delete
    suspend fun deleteDebt(
        debt: Debt
    )

    /**
     * Inserts a new Transaction into the database.
     * @param transaction Transaction to be added
     */
    @Insert
    suspend fun insertTransaction(
        transaction: com.example.spotme.database.Transaction
    ): Long?


    /**
     * Updates a transaction in the database
     * @param transaction the transaction to be updated.
     */
    @Update
    suspend fun updateTransaction(
        transaction: com.example.spotme.database.Transaction
    )

    /**
     * Deletes a transaction from the database.
     * @param transaction the transaction to be deleted
     */
    @Delete
    suspend fun deleteTransaction(
        transaction: com.example.spotme.database.Transaction
    )


}