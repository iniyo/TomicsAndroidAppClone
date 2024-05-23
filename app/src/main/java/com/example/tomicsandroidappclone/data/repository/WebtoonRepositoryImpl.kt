package com.example.tomicsandroidappclone.data.repository

import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.tomicsandroidappclone.data.database.WebtoonDao
import com.example.tomicsandroidappclone.data.network.WebtoonApi
import com.example.tomicsandroidappclone.domain.model.Webtoon
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton // For Hilt
class WebtoonRepositoryImpl @Inject constructor(
    private val webtoonDao: WebtoonDao,
    private val api: WebtoonApi
) : WebtoonRepository {
    override suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon> {
        val webtoons = api.getTodayWebtoon(0, 0, service, updateDay).webtoons

        return webtoons.filter { it.img.isNotEmpty() }.distinctBy { it.webtoonId } as ArrayList
    }
    /**
     * cache data
     **/
    /*override suspend fun getWebtoonsByCategory(category: String): List<Webtoon> {
        return webtoonDao.getWebtoonsByCategory(category)
    }
    override suspend fun insertWebtoons(webtoons: List<Webtoon>) {
        webtoonDao.insertAll(webtoons)
    }*/
    /**
     * PAGING
     **/
    override fun getAllToonPagingData(): Flow<PagingData<Webtoon>> {
        return Pager(PagingConfig(pageSize = 2)) {
            Log.d("TAG", "getAllToonPagingData: ")
            WebtoonPagingSource(api)
        }.flow
    }

    override fun getDayByWebtoonsForPaging(today: String): Flow<PagingData<Webtoon>> {
        Log.d("TAG", "getDayByWebtoonsForPaging: ")
        return Pager(PagingConfig(pageSize = 2)) {
            val webtoonPagingSource = WebtoonPagingSource(api)
            webtoonPagingSource.dataSetting(today)
            webtoonPagingSource
        }.flow
    }
}
