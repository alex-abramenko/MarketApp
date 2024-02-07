package com.alxabr.auth.data.repository

import com.alxabr.auth.data.data_source.UserModel
import com.alxabr.auth.domain.model.User
import javax.inject.Inject

internal class UserMapper @Inject constructor() {

    operator fun invoke(user: User): UserModel =
        UserModel(
            name = user.name,
            surname = user.surname,
            number = user.number
        )
}