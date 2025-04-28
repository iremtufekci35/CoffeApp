package com.example.coffeapp.data.local.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.coffeapp.data.model.Coffee

@Dao
interface CoffeeDao {
    @Insert
    fun insertCoffee(coffee: Coffee)

    @Query("SELECT * FROM coffees WHERE id = :coffeeId")
    fun getCoffeeById(coffeeId: Int): Coffee

    @Query("SELECT * FROM coffees")
    fun getAllCoffees(): List<Coffee>
}
