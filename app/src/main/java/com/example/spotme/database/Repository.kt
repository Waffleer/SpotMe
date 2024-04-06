package com.example.spotme.database

import kotlinx.coroutines.flow.Flow



/**
 * Interface outlining SubRepository class
 */
interface RepositoryInterface { //
    fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>
    //TODO change so that this grabs a specific debt's transactions
    fun getDebtWithTransactions(): Flow<List<DebtWithTransactions>>

    fun getTotalBalance(): Flow<Double>

    suspend fun insertProfile(profile: Profile)

}

/**
 * Aggregates all data sources (in this case just the one).
 *
 * Overrides the methods outlined by the SandwichRepositoryInterface
 * by essentially wrapping them around the subsDAO methods.
 *
 * @param subsDao used to access database data.
 */
class Repository(val dao: DataAccessObject):
    RepositoryInterface { // TODO currently setup for sandwiches. Change that.

    override fun getProfilesWithDebts(): Flow<List<ProfileWithDebts>>
        = dao.getProfilesWithDebts()

    // TODO modify to return a particular debt with it's transactions
    override fun getDebtWithTransactions(): Flow<List<DebtWithTransactions>>
        = dao.getDebtWithTransactions()


    override fun getTotalBalance(): Flow<Double>
        = dao.getTotalBalance()

    override suspend fun insertProfile(profile: Profile) {
        dao.insertProfile(profile)
    }
    /**
     * Provides a SubRepository instance
     * @return the singular instance of SubRepository
     */
    companion object{
        private var repository:RepositoryInterface? = null
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