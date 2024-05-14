package com.example.tomicsandroidappclone.data.repository

import androidx.paging.PagingData
import com.example.tomicsandroidappclone.domain.model.ToonResponse
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow


interface WebtoonRepository {
    suspend fun getWebtoonsByCategory(category: String): List<Webtoon>
    suspend fun insertWebtoons(webtoons: List<Webtoon>)
    suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon>
    fun getAllToonPagingData(): Flow<PagingData<Webtoon>>
    fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>>
}

