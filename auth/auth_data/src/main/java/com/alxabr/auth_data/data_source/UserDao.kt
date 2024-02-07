package com.alxabr.auth_data.data_source

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface UserDao {

    @Query("SELECT * FROM UserModel WHERE id = :userModelId")
    suspend fun getUser(userModelId: Long): UserModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(userModel: UserModel)
}