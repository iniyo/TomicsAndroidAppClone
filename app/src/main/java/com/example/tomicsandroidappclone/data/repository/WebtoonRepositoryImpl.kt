package com.example.tomicsandroidappclone.data.repository

import com.example.tomicsandroidappclone.data.api.WebtoonApi
import com.example.tomicsandroidappclone.domain.entity.ToonResponse
import com.example.tomicsandroidappclone.domain.repository.WebtoonRepository
import javax.inject.Inject
import javax.inject.Singleton


@Singleton // For Hilt
class WebtoonRepositoryImpl @Inject constructor(private val api: WebtoonApi) : WebtoonRepository {
    override suspend fun getWebtoon(page: Int, perPage: Int, service: String, updateDay: String): ToonResponse {
        return api.getWebtoon(page, perPage, service, updateDay)
    }
/*    suspend fun getWebtoons():ToonResponse {
        return api.getWebtoons()
    }*/
}
