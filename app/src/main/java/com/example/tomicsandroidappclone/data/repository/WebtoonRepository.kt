package com.example.tomicsandroidappclone.data.repository

import com.example.tomicsandroidappclone.domain.model.ToonResponse
import com.example.tomicsandroidappclone.domain.model.Webtoon


interface WebtoonRepository {
    suspend fun getWebtoon(
        perPage: Int,
        page: Int,
        service: String,
        updateDay: String
    ): ToonResponse

    suspend fun getDayByWebtoons(service: String, updateDay: String): ArrayList<Webtoon>
    suspend fun getKeywordByWebtoons(keyword: String): ToonResponse

    /*   fun getWebtoon(perPage: Int, page: Int, service: String, updateDay: String): ToonResponse? // 반환 타입 변경
       fun getDayByWebtoons(service: String, updateDay: String): ToonResponse
       fun getKeywordByWebtoons(keyword: String): Call<ToonResponse>*/
}

