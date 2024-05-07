package com.example.tomicsandroidappclone.data.repository

import androidx.paging.PagingData
import com.example.tomicsandroidappclone.domain.model.ToonResponse
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow


interface WebtoonRepository {

    suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon>
    fun getAllToonPagingData(): Flow<PagingData<Webtoon>>
    fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>>
}

