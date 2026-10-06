package com.kheang.firebaseauthenticationloginregister.di

import android.app.Application
import androidx.room.Room
import com.kheang.firebaseauthenticationloginregister.domain.database.AppDatabase
import com.kheang.firebaseauthenticationloginregister.domain.database.CartDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): AppDatabase{

        return Room.databaseBuilder(
            app,
            AppDatabase::class.java,
            "ecommerce_db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(db: AppDatabase): CartDao = db.cartDao()
}