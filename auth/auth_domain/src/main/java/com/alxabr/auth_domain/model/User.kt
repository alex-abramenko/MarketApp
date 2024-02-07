package com.alxabr.auth_domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val name: String,
    val surname: String,
    val number: String
): Parcelable