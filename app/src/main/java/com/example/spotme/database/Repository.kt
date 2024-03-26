package com.example.spotme.database

import kotlinx.coroutines.flow.Flow



/**
 * Interface outlining SubRepository class
 */
interface RepositoryInterface { // TODO currently setup for sandwiches. Change that.
    fun getSandwiches(): Flow<List<Sandwich>>
    suspend fun insertSandwich(sub: Sandwich)
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

    /**
     * Gets a Flow of a List of Sandwiches.
     */
    override fun getSandwiches(): Flow<List<Sandwich>>
            = dao.getAll()

    /**
     * Inserts a sandwich into the database.
     * @param sub the Sandwich to be inserted.
     */
    override suspend fun insertSandwich(sub: Sandwich)
            = dao.insertSandwich(sub)

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