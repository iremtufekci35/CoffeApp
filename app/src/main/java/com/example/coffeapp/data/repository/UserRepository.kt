package com.example.coffeapp.data.repository

import com.example.coffeapp.data.local.db.dao.UserDao
import com.example.coffeapp.data.model.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {

    suspend fun insertUser(user: User) {
        return withContext(Dispatchers.IO) {
            userDao.insertUser(user)
        }
    }

    suspend fun getUserById(userId: Int): User {
        return userDao.getUserById(userId)
    }

    suspend fun login(username: String, password: String): User? {
        return withContext(Dispatchers.IO) {
            userDao.getUserByUsernameAndPassword(username, password)
        }
    }
}
