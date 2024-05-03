package com.example.tomicsandroidappclone.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tomicsandroidappclone.data.remote.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.model.ToonResponse
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // For Hilt
class WebtoonRepositoryImpl @Inject constructor(private val api: WebtoonApi) : WebtoonRepository {
    override suspend fun getWebtoon(
        perPage: Int,
        page: Int,
        service: String,
        updateDay: String
    ): ToonResponse {
        return api.getWebtoon(perPage, page, service, updateDay)
    }

    override suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon> {
        return api.getWebtoon(0, 0, service, updateDay).webtoons
    }

    override suspend fun getKeywordByWebtoons(keyword: String): ToonResponse {
        return api.getSearch(keyword)
    }
    override fun getAllToonPagingData(): Flow<PagingData<Webtoon>> {
        return Pager(PagingConfig(pageSize = 10)) {
            WebtoonPagingSource(api)
        }.flow
    }

    override fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>> {
        return Pager(PagingConfig(pageSize = 10)) {
            val webtoonPaging = WebtoonPagingSource(api)
            webtoonPaging.dataSetting(today,"","","","")
            webtoonPaging
        }.flow
    }
}
