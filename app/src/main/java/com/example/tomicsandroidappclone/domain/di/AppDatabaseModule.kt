package com.example.tomicsandroidappclone.domain.di

import android.app.Application
import androidx.room.Room
import com.example.tomicsandroidappclone.data.database.AppDatabase
import com.example.tomicsandroidappclone.data.database.WebtoonDao
import com.example.tomicsandroidappclone.data.remote.api.WebtoonApi
import com.example.tomicsandroidappclone.data.repository.WebtoonRepository
import com.example.tomicsandroidappclone.data.repository.WebtoonRepositoryImpl
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
    fun provideDatabase(app: Application): WebtoonDao {
        val database = Room.databaseBuilder(app, AppDatabase::class.java, "webtoon-database")
            .fallbackToDestructiveMigration()
            .build()

        return database.webtoonDao()
    }
    @Provides
    @Singleton
    fun provideWebtoonDataSource(
        webtoonDao: WebtoonDao,
        webtoonApi: WebtoonApi
    ): WebtoonRepository {
        return WebtoonRepositoryImpl(webtoonDao, webtoonApi)
    }
}