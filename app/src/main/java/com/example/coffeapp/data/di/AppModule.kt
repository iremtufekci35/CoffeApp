package com.example.coffeapp.data.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.coffeapp.data.api.ApiService
import com.example.coffeapp.data.local.datastore.DataStoreManager
import com.example.coffeapp.data.local.db.AppDatabase
import com.example.coffeapp.data.local.db.AppDatabaseImpl
import com.example.coffeapp.data.local.db.dao.CartDao
import com.example.coffeapp.data.local.db.dao.UserDao
import com.example.coffeapp.data.utils.Constants.Companion.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.X509Certificate
import javax.inject.Singleton
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.TrustManager
import javax.net.ssl.X509TrustManager

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
        )
            .fallbackToDestructiveMigration()
            .addMigrations(MIGRATION_1_3)
            .build()
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
    fun provideDataStoreManager(@ApplicationContext context: Context) : DataStoreManager {
        return DataStoreManager(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: AppDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideCartDao(database: AppDatabase): CartDao {
        return database.cartDao()
    }

    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .sslSocketFactory(createInsecureSocketFactory(), createInsecureTrustManager())
            .hostnameVerifier { _, _ -> true }
            .build()
    }

    @Provides
    fun provideApiService(okHttpClient: OkHttpClient): ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    private fun createInsecureSocketFactory(): SSLSocketFactory {
        val trustAllCerts = arrayOf<TrustManager>(createInsecureTrustManager())
        val sc = SSLContext.getInstance("TLS")
        sc.init(null, trustAllCerts, SecureRandom())
        return sc.socketFactory
    }

    private fun createInsecureTrustManager(): X509TrustManager {
        return object : X509TrustManager {
            override fun getAcceptedIssuers(): Array<X509Certificate> = arrayOf()
            override fun checkClientTrusted(chain: Array<X509Certificate>, authType: String) {}
            override fun checkServerTrusted(chain: Array<X509Certificate>, authType: String) {}
        }
    }
}
