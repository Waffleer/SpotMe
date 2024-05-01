package com.example.spotme.data

import androidx.annotation.StringRes
import com.example.spotme.R

enum class PaymentType(val title: String) {
    CASH("Cash"),
    PAYPAL("PayPal"),
    VENMO("Venmo"),
    MONERO("Monero"),
    APPLEPAY("Apple Pay"),
    GOOGLEPAY("Google Pay"),
    NONE("None"),
}