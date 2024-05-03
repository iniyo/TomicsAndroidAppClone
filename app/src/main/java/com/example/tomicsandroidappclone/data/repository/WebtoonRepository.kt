package com.example.tomicsandroidappclone.data.repository

import androidx.paging.PagingData
import com.example.tomicsandroidappclone.domain.model.ToonResponse
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow


interface WebtoonRepository {
    suspend fun getWebtoon(
        perPage: Int,
        page: Int,
        service: String,
        updateDay: String
    ): ToonResponse
    suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon>
    suspend fun getKeywordByWebtoons(keyword: String): ToonResponse
    fun getAllToonPagingData(): Flow<PagingData<Webtoon>>
    fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>>
}

