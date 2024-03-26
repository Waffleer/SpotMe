package com.example.spotme.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
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