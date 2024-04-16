package com.example.spotme.database

import kotlinx.coroutines.flow.Flow


/**
 * Interface outlining SubRepository class
 */
interface RepositoryInterface { //

     // Gets ALL profiles with ALL of their debts and transactions
    fun getEverything(): Flow<List<ProfileWithEverything>>
    //Gets SPECIFIC profile with ALL debts and transactions
    fun getProfiles(): Flow<List<Profile>>
    //Gets list of all profiles without debts
    fun getSpecificProfileWithEverything(profileId: Long?): Flow<ProfileWithEverything>
    //Get ALL profiles with ALL debts
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>
    // Gets A SPECIFIC debt with all of it's transactions
    fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions>
    // Get the user's total balance
    fun getTotalBalance(): Flow<Double>
    // Get the id, name, and balance of the guy who owes you the most money
    fun getLargestDebtor(): Flow<Profile>
    // Get the id, name, and balance of the guy who you owe the most money
    fun getLargestCreditor(): Flow<Profile>
    // Get the oldest debt
    fun getOldestDebt(): Flow<Debt>

    suspend fun insertProfile(profile: Profile): Long?
    suspend fun insertDebt(debt: Debt)
    suspend fun insertTransaction(transaction: Transaction)
}

/**
 * Aggregates all data sources (in this case just the one).
 *
 * Overrides the methods outlined by the SandwichRepositoryInterface
 * by essentially wrapping them around the subsDAO methods.
 *
 * @param dao used to access database data.
 */
class Repository(val dao: DataAccessObject):
    RepositoryInterface {

    override fun getEverything(): Flow<List<ProfileWithEverything>>
        = dao.getEverything()
    override fun getProfiles(): Flow<List<Profile>>
            = dao.getProfiles()
    override fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>
        = dao.getProfilesWithDebts()
    override fun getSpecificProfileWithEverything(profileId: Long?): Flow<ProfileWithEverything>
         = dao.getSpecificProfileWithEverything(profileId)
    override fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions>
        = dao.getDebtWithTransactions(debtId)
    override fun getTotalBalance(): Flow<Double>
        = dao.getTotalBalance()
    override fun getLargestDebtor(): Flow<Profile>
        = dao.getLargestDebtor()
    override fun getLargestCreditor(): Flow<Profile>
        = dao.getLargestCreditor()
    override fun getOldestDebt(): Flow<Debt>
        = dao.getOldestDebt()

    override suspend fun insertProfile(profile: Profile): Long? {
        return dao.insertProfile(profile)
    }
    override suspend fun insertDebt(debt: Debt) {
        dao.insertDebt(debt)
    }
    override suspend fun insertTransaction(transaction: Transaction) {
        dao.insertTransaction(transaction)
    }
    /**
     * Provides a SubRepository instance
     * @return the singular instance of SubRepository
     */
    companion object {
        private var repository: RepositoryInterface? = null
        fun getRepository(database: LocalDatabase):
                RepositoryInterface {
            if (repository == null) {
                repository = Repository(
                    database.getDao()
                )
            }
            return repository!!
        }
    }
}