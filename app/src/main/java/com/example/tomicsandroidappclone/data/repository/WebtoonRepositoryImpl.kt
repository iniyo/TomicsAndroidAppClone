package com.example.tomicsandroidappclone.data.repository

import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.entity.Webtoon
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // For Hilt
class WebtoonRepositoryImpl @Inject constructor(private val api: WebtoonApi) : WebtoonRepository {
    override suspend fun getWebtoon(perPage: Int, page: Int, service: String, updateDay: String): ToonResponse {
        return api.getWebtoon(perPage, page, service, updateDay)
    }
    override suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon> {
        return api.getWebtoon(0, 0, service, updateDay).webtoons as ArrayList<Webtoon>
    }

    override suspend fun getKeywordByWebtoons(keyword: String): ToonResponse {
        return api.getSearch(keyword)
    }

}
