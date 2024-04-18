package com.example.tomicsandroidappclone.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.android.hilt.data.Log
import com.example.android.hilt.data.LogDao


@Database(entities = [Log::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun logDao(): LogDao
}