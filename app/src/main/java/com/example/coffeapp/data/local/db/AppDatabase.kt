package com.example.coffeapp.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.coffeapp.data.local.db.dao.CartDao
import com.example.coffeapp.data.local.db.dao.CoffeeDao
import com.example.coffeapp.data.local.db.dao.ProductDao
import com.example.coffeapp.data.local.db.dao.UserDao
import com.example.coffeapp.data.model.CartItem
import com.example.coffeapp.data.model.Coffee
import com.example.coffeapp.data.model.Product
import com.example.coffeapp.data.model.User

@Database(entities = [User::class, Coffee::class, CartItem::class, Product::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun coffeeDao(): CoffeeDao
    abstract fun cartDao(): CartDao
    abstract fun productDao(): ProductDao
}
