package com.example.spotme.database

import kotlinx.coroutines.flow.Flow


/**
 * Interface outlining SubRepository class
 */
interface RepositoryInterface { //
    fun getEverything(): Flow<List<ProfileWithEverything>>
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>
    fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions>
    fun getTotalBalance(): Flow<Double>
    fun getLargestDebtor(): Flow<ProfileDebtTuple>
    fun getLargestCreditor(): Flow<ProfileDebtTuple>
    fun getOldestDebt(): Flow<Debt>

    suspend fun insertProfile(profile: Profile)
    suspend fun insertDebt(debt: Debt)
    suspend fun insertTransaction(transaction: Transaction)
}

/**
 * Aggregates all data sources (in this case just the one).
 *
 * Overrides the methods outlined by the SandwichRepositoryInterface
 * by essentially wrapping them around the subsDAO methods.
 *
 * @param subsDao used to access database data.
 */
class Repository(val dao: DataAccessObject) :
    RepositoryInterface { // TODO currently setup for sandwiches. Change that.

    override fun getEverything(): Flow<List<ProfileWithEverything>> = dao.getEverything()
    override fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>> = dao.getProfilesWithDebts()
    override fun getDebtWithTransactions(debtId: Long?): Flow<DebtWithTransactions> =
        dao.getDebtWithTransactions(debtId)

    override fun getTotalBalance(): Flow<Double> = dao.getTotalBalance()
    override fun getLargestDebtor(): Flow<ProfileDebtTuple> = dao.getLargestDebtor()
    override fun getLargestCreditor(): Flow<ProfileDebtTuple> = dao.getLargestCreditor()
    override fun getOldestDebt(): Flow<Debt> = dao.getOldestDebt()
    override suspend fun insertProfile(profile: Profile) {
        dao.insertProfile(profile)
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