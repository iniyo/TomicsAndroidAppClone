package com.example.tomicsandroidappclone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tomicsandroidappclone.data.database.converter.EntityConverter
import com.example.tomicsandroidappclone.domain.model.Webtoon

@Database(entities = [Webtoon::class], version = 1)
@TypeConverters(EntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webtoonDao(): WebtoonDao
}