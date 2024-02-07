package com.alxabr.auth.data.repository

import com.alxabr.auth.data.data_source.CURRENT_USER_ID
import com.alxabr.auth.data.data_source.UserDao
import com.alxabr.auth.domain.model.User
import com.alxabr.auth.domain.repository.AuthRepository
import com.alxabr.auth.utils.AuthLogger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

internal class AuthRepositoryImpl @Inject constructor(
    private val userDao: UserDao,
    private val userMapper: UserMapper
) : AuthRepository {

    private val dispatcher = Dispatchers.IO

    override suspend fun checkAuthorization(): Boolean =
        withContext(dispatcher) {
            var isLogin = false
            try {
                isLogin = userDao.getUser(userModelId = CURRENT_USER_ID) != null
            } catch (ex: Exception) {
                AuthLogger.error(ex.stackTraceToString())
            }
            return@withContext isLogin
        }

    override suspend fun login(user: User): Boolean =
        withContext(dispatcher) {
            var isLogin = true
            try {
                userDao.insertUser(userModel = userMapper(user))
            } catch (ex: Exception) {
                AuthLogger.error(ex.stackTraceToString())
                isLogin = false
            }
            return@withContext isLogin
        }
}