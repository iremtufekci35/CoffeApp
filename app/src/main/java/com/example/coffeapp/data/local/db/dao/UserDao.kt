package com.example.coffeapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.coffeapp.data.model.Users

@Dao
interface UserDao {

   @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(users: Users)

//    @Query("SELECT * FROM users WHERE id = :userId")
//    suspend fun getUserById(userId: Int): User
//
//    @Query("SELECT * FROM users WHERE username = :username AND password = :password")
//    suspend fun getUserByUsernameAndPassword(username: String, password: String): User
}
