package com.example.tomicsandroidappclone.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.tomicsandroidappclone.data.database.converter.EntityConverter
import com.example.tomicsandroidappclone.domain.model.ToonImage

/*@Database(entities = [Webtoon::class], version = 1)*/
@Database(entities = [ToonImage::class], version = 1)
@TypeConverters(EntityConverter::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun webtoonDao(): WebtoonDao
    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                val newInstance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                ).build()
                instance = newInstance
                newInstance
            }
        }
    }
}