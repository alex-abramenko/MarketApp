package com.alxabr.auth_data.repository

import com.alxabr.auth_domain.model.User
import com.alxabr.auth_data.data_source.UserEntity
import javax.inject.Inject

internal class UserMapper @Inject constructor() {

    operator fun invoke(user: User): UserEntity =
        UserEntity(
            name = user.name,
            surname = user.surname,
            number = user.number
        )

    operator fun invoke(userEntity: UserEntity): User =
        User(
            name = userEntity.name,
            surname = userEntity.surname,
            number = userEntity.number
        )
}