package com.example.ecommerce_shoes.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

class User (
    val name: String,
    val image: Int,
    val status: Boolean = false
        )
