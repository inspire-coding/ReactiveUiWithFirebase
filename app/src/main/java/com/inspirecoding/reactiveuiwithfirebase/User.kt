package com.inspirecoding.reactiveuiwithfirebase

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val username : String = ""
) : Parcelable
