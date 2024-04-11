package com.example.spotme.data

import androidx.annotation.StringRes
import com.example.spotme.R

enum class PaymentType(val title: String) {
    PAYPAL("paypal"),
    VENMO("venmo"),
    MONERO("monero"),
    NONE("none"),
}