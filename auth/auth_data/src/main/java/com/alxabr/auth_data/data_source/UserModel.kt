package com.alxabr.auth_data.data_source

import androidx.room.Entity
import androidx.room.PrimaryKey

internal const val CURRENT_USER_ID = 1L

@Entity
internal class UserModel(
    @PrimaryKey val id: Long = CURRENT_USER_ID,
    val name: String,
    val surname: String,
    val number: String
)