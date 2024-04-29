package com.example.tomicsandroidappclone.data.database

import android.util.Log
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Log::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {}