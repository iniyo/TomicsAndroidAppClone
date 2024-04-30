package com.example.tomicsandroidappclone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.data.database.WebtoonPagingSource
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PagingRepository @Inject constructor(
    private val service: WebtoonApi
) {

    fun getPagingData(): Flow<PagingData<Webtoon>> {
        return Pager(PagingConfig(pageSize = 10)) {
            WebtoonPagingSource(service)
        }.flow
    }

}