package com.example.tomicsandroidappclone.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.tomicsandroidappclone.domain.model.Webtoon

@Dao
interface WebtoonDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(webtoons: List<Webtoon>)

    @Query("SELECT * FROM webtoons WHERE category = :category")
    suspend fun getWebtoonsByCategory(category: String): List<Webtoon>

    @Query("DELETE FROM webtoons WHERE category = :category")
    suspend fun deleteWebtoonsByCategory(category: String)
}