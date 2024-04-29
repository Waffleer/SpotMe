package com.example.spotme.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

// <--- ENTITIES --->
/**
 * Contains profile information
 * @property profileId primary key
 * @property name the profile's name
 * @property description a description of the profile
 * @property paymentPreference the profile's preferred payment method
 * @property totalDebt th profile's debt balance
 */
@Entity
data class Profile(
    @PrimaryKey val profileId: Long?,
    val name: String,
    val description: String,
    val paymentPreference: String,
    val totalDebt: Double,
    val createdDate: Date,
)

/**
 * Contains debt information.
 *
 * @property debtId primary key
 * @property f_profile_id the id of the profile that the debt belongs to
 * @property name the name of the debt
 * @property totalDebt the total balance of the debt
 * @property description the description of the debt
 * @property canceled whether the debt is canceled
 * @property createdDate when the debt was created
 */
@Entity
data class Debt(
    @PrimaryKey val debtId: Long?,
    val f_profile_id: Long?,
    val name: String,
    val totalDebt: Double,
    val description: String,
    val canceled: Boolean,
    val createdDate: Date,
)

/**
 * Contains transaction information.
 * @property transactionId primary key
 * @property f_debt_id the id of the debt that the transaction belongs to
 * @property createdDate when the transaction was created
 * @property amount how much money was involved in the transaction
 * @property description a description of the transaction
 * @property canceld whether the transaction has been canceled
 */
@Entity
data class Transaction(
    @PrimaryKey val transactionId: Long?,
    val f_debt_id: Long?, //Foreign Key
    val createdDate: Date,
    val amount: Double,
    val description: String,
    val canceled: Boolean,
)

/**
 * Establishes the relationship between debts and transactions.
 */
@Entity(primaryKeys = ["debtId","transactionId"])
data class DebtTransactionsCrossRef(
    val debtId: Long?,
    val transactionId: Long?
)


// <--- RELATIONAL OBJECTS --->
/**
 * Relationship: Profile -< Debt
 * Creates a one to many relationship between Profile's and Debts
 */
data class ProfileWithDebts(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "profileId",
        entityColumn = "f_profile_id"
    )
    val debts: List<Debt>
)

/**
 * Relationship: Debt -< Transaction
 * Creates a one to many relationship between Debts and Transactions
 */
data class DebtWithTransactions(
    @Embedded val debt: Debt,
    @Relation(
        parentColumn = "debtId",
        entityColumn = "f_debt_id",
    )
    val transactions: List<Transaction>
)

/**
 * Creates an object that contains all debts and
 * transactions associated with a profile.
 */
data class ProfileWithEverything(
    @Embedded val profile: Profile,
    @Relation(
        entity = Debt::class,
        parentColumn = "profileId",
        entityColumn = "f_profile_id"
    )
    val debtsWithTransactions: List<DebtWithTransactions>
)