package com.example.coffeapp.data.di

import android.app.Application
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.coffeapp.data.local.db.AppDatabase
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.local.db.dao.CartDao
import com.example.coffeapp.data.local.db.dao.CoffeeDao
import com.example.coffeapp.data.local.db.dao.ProductDao
import com.example.coffeapp.data.local.db.dao.UserDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase {
        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "coffee_shop_db"
        ).fallbackToDestructiveMigration().addMigrations(MIGRATION_1_3).build()
    }
    val MIGRATION_1_3 = object : Migration(1, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
            database.execSQL("ALTER TABLE CartItem ADD COLUMN discount REAL NOT NULL DEFAULT 0.0")
        }
    }

    @Provides
    @Singleton
    fun provideAppDatabaseImpl(appDatabase: AppDatabase): AppDatabaseImpl {
        return AppDatabaseImpl(appDatabase)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideCoffeeDao(database: AppDatabase): CoffeeDao {
        return database.coffeeDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    @Singleton
    fun provideProductDao(database: AppDatabase): ProductDao {
        return database.productDao()
    }
}


