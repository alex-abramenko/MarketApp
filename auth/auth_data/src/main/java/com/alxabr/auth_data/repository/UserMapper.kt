package com.alxabr.auth_data.repository

import com.alxabr.auth_domain.model.User
import com.alxabr.auth_data.data_source.UserModel
import javax.inject.Inject

internal class UserMapper @Inject constructor() {

    operator fun invoke(user: User): UserModel =
        UserModel(
            name = user.name,
            surname = user.surname,
            number = user.number
        )
}