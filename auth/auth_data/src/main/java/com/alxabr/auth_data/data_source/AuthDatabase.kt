package com.alxabr.auth_data.data_source

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [UserEntity::class],
    version = 1
)
internal abstract class AuthDatabase : RoomDatabase() {

    abstract val userDao: UserDao
}