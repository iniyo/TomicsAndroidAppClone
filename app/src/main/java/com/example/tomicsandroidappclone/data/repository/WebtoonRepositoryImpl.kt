package com.example.tomicsandroidappclone.data.repository

import android.util.Log
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
    private var pageSize = 0

    override suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon> {
        return api.getTodayWebtoon(0, 0, service, updateDay).webtoons
    }
    /**
     * PAGING
     **/
    override fun getAllToonPagingData(): Flow<PagingData<Webtoon>> {
        return Pager(PagingConfig(pageSize = 50)) {
            Log.d("TAG", "getAllToonPagingData: ")
            WebtoonPagingSource(api)
        }.flow
    }

    override fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>> {
        Log.d("TAG", "getDayByWebtoonsForPaging: ")
        return Pager(PagingConfig(pageSize = 5)) {
            val webtoonPagingSource = WebtoonPagingSource(api)
            webtoonPagingSource.dataSetting(today, "", "", "", "")
            webtoonPagingSource
        }.flow
    }
}
