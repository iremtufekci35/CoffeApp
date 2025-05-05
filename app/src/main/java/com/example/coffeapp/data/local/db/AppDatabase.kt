package com.example.coffeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coffeapp.data.local.db.dao.CartDao
import com.example.coffeapp.data.local.db.dao.UserDao
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.Users

@Database(entities = [Users::class, CartItem::class], version = 3)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
}
