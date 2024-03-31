package com.example.spotme.database

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import androidx.room.Relation
import java.util.Date

/**
 * Defines the Sandwich table for the database.
 * @property uid unique identifier and primary key for the sandwich.
 * @property subName stores the name of the sub.
 * @property price stores the price of the sub.
 * @property date stores the date and time that the sub was purchased.
 */
@Entity // TODO leaving this as an example for now. Replace soon.
data class Sandwich(
    @PrimaryKey val uid: Long?,
    @ColumnInfo(name = "sub_name")
    val subName: String,
    @ColumnInfo(name = "price")
    val price: Double,
    @ColumnInfo(name = "date")
    val date: Date
)

@Entity
data class Profile(
    @PrimaryKey val profileId: Long?,
    @ColumnInfo(name="name")
    val name: String,
    @ColumnInfo(name="bio")
    val bio: String,
    @ColumnInfo(name="payment_preference")
    val paymentPreference: String,
    @ColumnInfo("debt_issue_date")
    val date: Date
)

@Entity
data class Debt(
    @PrimaryKey val debtId: Long?,
    @ColumnInfo(name="f_profile_id") //Foreign Key
    val ProfileIdF: Long?
)

@Entity
data class Transaction(
    @PrimaryKey val transaction_id: Long?,
    @ColumnInfo(name="f_debt_id") //Foreign Key
    val debtId: Long?,
    @ColumnInfo(name="date")
    val date: Date,
    @ColumnInfo(name="amount_owed")
    val amount: Double,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name="status")
    val canceled: Boolean
)

//Relationship: Profile -< Debt
@Entity
data class ProfileWithDebts(
    @Embedded val profile: Profile,
    @Relation(
        parentColumn = "profileId",
        entityColumn = "f_profile_id"
    )
    val debts: List<Debt>
)

//Relationship: Debt -< Transaction
@Entity
data class DebtWithTransactions(
    @Embedded val debt: Debt,
    @Relation(
        parentColumn = "debtId",
        entityColumn = "f_debt_id",
    )
    val transactions: List<Transaction>
)